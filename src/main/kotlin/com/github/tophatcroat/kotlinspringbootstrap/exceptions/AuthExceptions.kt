package com.github.tophatcroat.kotlinspringbootstrap.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Missing or incorrect token")
class AuthException : RuntimeException()

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect username or password")
class CredentialsException : RuntimeException()