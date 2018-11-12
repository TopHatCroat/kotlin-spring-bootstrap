package com.github.tophatcroat.kotlinspringbootstrap.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Missing or incorrect token")
class AuthException : RuntimeException() {
    override val message: String?
        get() = "Missing or incorrect token"
}

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect username or password")
class CredentialsException : RuntimeException() {
    override val message: String?
        get() = "Incorrect username or password"
}
