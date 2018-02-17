package com.github.tophatcroat.kotlinspringbootstrap.helpers

import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import org.springframework.validation.Errors

fun Errors.check() {
    if (hasFieldErrors())
        throw InvalidDataException(this)

}