package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.exception.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.service.ProfileService
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginRequest
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginResponse
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.ApacheHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.security.auth.message.AuthException


@RestController
class IndexController(
        val repository: UserRepository,
        val userService: UserService,
        val profileService: ProfileService,
        @Value("\${security.oauth2.client.clientId}") val clientId: String,
        @Value("\${security.oauth2.client.clientSecret}") val clientSecret: String) {

    @GetMapping(value = ["/"], consumes = ["*", MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseStatus(value = HttpStatus.FOUND)
    fun home() = ResponseEntity("", HttpHeaders().apply {
        location = URI("/swagger-ui.html")
    }, HttpStatus.FOUND)

    @GetMapping("/ping")
    @ResponseStatus(value = HttpStatus.OK)
    fun ping() = "pong"


    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Validated @RequestBody body: UserLoginRequest, errors: BindingResult): UserLoginResponse {
        errors.check()

        if (repository.existsByEmail(body.email!!))
            throw UserExistsException()

        return userService.register(body.email, body.password!!).let {
            UserLoginResponse(it.id!!, it.email)
        }
    }

    @GetMapping("/gauth")
    @ResponseStatus(value = HttpStatus.OK)
    fun googleOauth(@RequestParam idToken: String): UserLoginResponse {
        val verifier = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), JacksonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(listOf(clientId))
                .build()

        // (Receive idTokenString by HTTPS POST)
        val idToken = verifier.verify(idToken)
        if (idToken != null) {
            val payload = idToken.payload
            val userId = payload.subject
            // Get profile information from payload
            val email = payload.email
            val emailVerified = payload.emailVerified as Boolean
            val name = payload["name"] as String
            val pictureUrl = payload["picture"] as String
            val familyName = payload["family_name"] as String
            val givenName = payload["given_name"] as String

            if (repository.existsByEmail(email))
                throw UserExistsException()

            val userResponse = userService.registerWithOauth(email, idToken.toString()).let {
                UserLoginResponse(it.id!!, it.email)
            }

            profileService.createProfileForUser(userResponse.id, name, pictureUrl)

            return userResponse

        } else {
            throw AuthException()
        }

    }
}
