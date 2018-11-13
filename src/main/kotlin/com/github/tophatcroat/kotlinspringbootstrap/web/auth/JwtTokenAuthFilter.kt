package com.github.tophatcroat.kotlinspringbootstrap.web.auth


import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenAuthFilter(val userService: UserService,
                         @Value("\${security.signing-key}") val jwtSecret: String) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        val authorizationHeader = request.getHeader("Authorization")

        // 2. validate the header and check the prefix
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.replace("Bearer", "").trim()

        try {
            val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)

            val username = claims.body.subject
            if (username != null) {
                val authorities = claims.body.get("authorities") as List<String>

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities.map { authority -> SimpleGrantedAuthority(authority) }
                ).apply { details = userService.loadUserByUsername(username) }
            }

        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response)
    }

}
