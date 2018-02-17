package com.github.tophatcroat.kotlinspringbootstrap.helpers

import org.springframework.validation.Errors

fun Errors.check() {
    if (hasFieldErrors())
        throw RuntimeException()
}