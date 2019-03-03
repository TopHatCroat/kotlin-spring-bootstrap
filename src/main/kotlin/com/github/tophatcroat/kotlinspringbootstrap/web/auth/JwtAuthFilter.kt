package com.github.tophatcroat.kotlinspringbootstrap.web.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginRequest
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.util.*
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter(
        private val authManager: AuthenticationManager,
        @Value("\${security.signing-key}") val jwtSecret: String) : UsernamePasswordAuthenticationFilter() {

    init {
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/login", "POST"))
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = ObjectMapper().readValue(request.inputStream, UserLoginRequest::class.java)
            val authToken = UsernamePasswordAuthenticationToken(creds.email, creds.password, Collections.emptyList())

            return authManager.authenticate(authToken)
        } catch (e: Exception) {
            throw CredentialsException()
        }

    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
                                          chain: FilterChain, auth: Authentication) {
        val now = System.currentTimeMillis()
        val token = Jwts.builder()
                .setSubject(auth.name)
                .claim("authorities", listOf("USER"))
                .setIssuedAt(Date(now))
                .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()

        response.addHeader("Authorization", "Bearer $token")
    }
}
