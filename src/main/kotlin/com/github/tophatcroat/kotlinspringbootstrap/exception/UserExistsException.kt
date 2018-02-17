package com.github.tophatcroat.kotlinspringbootstrap.exception

import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User already exists")
class UserExistsException : RuntimeException()

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid request data")
class InvalidDataException(val errors: Errors) : RuntimeException()
