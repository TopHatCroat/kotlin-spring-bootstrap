package com.github.tophatcroat.kotlinspringbootstrap.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason="User already exists")
class UserExistsException : RuntimeException()