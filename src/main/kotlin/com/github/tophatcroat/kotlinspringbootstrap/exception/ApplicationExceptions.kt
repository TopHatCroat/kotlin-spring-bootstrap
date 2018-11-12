package com.github.tophatcroat.kotlinspringbootstrap.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "This should not happen. Congratulations!")
class WtfException : RuntimeException()

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Does not exist")
class DoesNotExistException : RuntimeException()

