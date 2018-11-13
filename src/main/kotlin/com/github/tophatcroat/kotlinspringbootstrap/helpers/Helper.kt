package com.github.tophatcroat.kotlinspringbootstrap.helpers

import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import com.github.tophatcroat.kotlinspringbootstrap.exception.AuthException
import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.Errors

fun Errors.check() {
    if (hasFieldErrors())
        throw InvalidDataException(this)

}

fun getUser() = (SecurityContextHolder.getContext().authentication.details as? User) ?: throw AuthException()

fun getUserEmail() = getUser().email

fun getUserId() = getUser().id
