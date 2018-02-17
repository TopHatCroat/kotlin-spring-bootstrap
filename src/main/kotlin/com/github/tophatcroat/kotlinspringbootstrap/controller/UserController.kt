package com.github.tophatcroat.kotlinspringbootstrap.controller

import com.github.tophatcroat.kotlinspringbootstrap.auth.RequireAuth
import com.github.tophatcroat.kotlinspringbootstrap.exceptions.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.exceptions.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.model.UserLoginRequest
import com.github.tophatcroat.kotlinspringbootstrap.model.UserLoginResponse
import com.github.tophatcroat.kotlinspringbootstrap.repository.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(val repository: UserRepository,
                     val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Valid @RequestBody body: UserLoginRequest, errors: BindingResult) {
        errors.check()

        if (repository.existsByName(body.name!!))
            throw UserExistsException()

        userService.register(body.name, body.password!!)
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    fun login(@Valid @RequestBody login: UserLoginRequest, errors: BindingResult): UserLoginResponse {
        errors.check()

        if (repository.existsByName(login.name!!).not())
            throw CredentialsException()

        return userService.login(login.name, login.password!!).let {
            repository.saveAndFlush(it)
            UserLoginResponse(it.name, it.token)
        }
    }

    @RequireAuth
    @GetMapping("/users")
    fun getAll() = repository.findAll().map { UserLoginResponse(it.name, it.token) }

    @RequireAuth
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long) = repository.getOne(id).let { UserLoginResponse(it.name, it.token) }
}