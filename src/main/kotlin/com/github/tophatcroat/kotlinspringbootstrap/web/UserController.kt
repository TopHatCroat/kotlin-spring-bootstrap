package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.web.auth.RequireAuth
import com.github.tophatcroat.kotlinspringbootstrap.web.data.InvalidDataResponse
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginRequest
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginResponse
import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import com.github.tophatcroat.kotlinspringbootstrap.exception.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse


@RestController
class UserController(val repository: UserRepository,
                     val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Validated @RequestBody body: UserLoginRequest, errors: BindingResult): UserLoginResponse {
        errors.check()

        if (repository.existsByEmail(body.email!!))
            throw UserExistsException()

        return userService.register(body.email, body.password!!).let {
            UserLoginResponse(it.id!!, it.email, it.token)
        }
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    fun login(@Validated @RequestBody login: UserLoginRequest, errors: BindingResult): UserLoginResponse {
        errors.check()

        if (repository.existsByEmail(login.email!!).not())
            throw CredentialsException()

        return userService.login(login.email, login.password!!).let {
            UserLoginResponse(it.id!!, it.email, it.token)
        }
    }

    @RequireAuth
    @GetMapping("/users")
    fun getAll() = repository.findAll().map { UserResponse(it.id!!, it.email) }

    @RequireAuth
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long) = repository.getOne(id).let { UserResponse(it.id!!, it.email) }
}