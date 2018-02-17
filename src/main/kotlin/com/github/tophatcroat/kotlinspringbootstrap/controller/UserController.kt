package com.github.tophatcroat.kotlinspringbootstrap.controller

import com.github.tophatcroat.kotlinspringbootstrap.auth.RequireAuth
import com.github.tophatcroat.kotlinspringbootstrap.controller.data.InvalidDataResponse
import com.github.tophatcroat.kotlinspringbootstrap.controller.data.UserLoginRequest
import com.github.tophatcroat.kotlinspringbootstrap.controller.data.UserLoginResponse
import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import com.github.tophatcroat.kotlinspringbootstrap.exception.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.repository.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Validation


@RestController
class UserController(val repository: UserRepository,
                     val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Validated @RequestBody body: UserLoginRequest, errors: BindingResult) {
        errors.check()

        if (repository.existsByEmail(body.email!!))
            throw UserExistsException()

        userService.register(body.email, body.password!!)
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    fun login(@Validated @RequestBody login: UserLoginRequest, errors: BindingResult): UserLoginResponse {
        errors.check()

        if (repository.existsByEmail(login.email!!).not())
            throw CredentialsException()

        return userService.login(login.email, login.password!!).let {
            repository.saveAndFlush(it)
            UserLoginResponse(it.email, it.token)
        }
    }

    @RequireAuth
    @GetMapping("/users")
    fun getAll() = repository.findAll().map { UserLoginResponse(it.email, it.token) }

    @RequireAuth
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long) = repository.getOne(id).let { UserLoginResponse(it.email, it.token) }

    @ExceptionHandler(InvalidDataException::class)
    fun handleInvalidDataException(exception: InvalidDataException): InvalidDataResponse {
        return InvalidDataResponse(
                Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request data",
                exception.errors.fieldErrors.map { it.field to (it.defaultMessage ?: "") }.toMap()
        )
    }
}