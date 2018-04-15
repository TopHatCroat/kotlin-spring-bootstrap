package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.exception.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import com.github.tophatcroat.kotlinspringbootstrap.web.auth.RequireAuth
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginRequest
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserLoginResponse
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import io.swagger.annotations.AuthorizationScope
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/", consumes = ["*", MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController(val repository: UserRepository,
                     val userService: UserService) {

    @GetMapping(consumes = [MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseStatus(value = HttpStatus.FOUND)
    fun home() = ResponseEntity("", HttpHeaders().apply {
        location = URI("/swagger-ui.html")
    }, HttpStatus.FOUND)


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
    @ApiOperation(value = "Get all users", authorizations = [Authorization(value = "api_key", scopes = [AuthorizationScope(scope = "global", description = "accessEverything")])])
    @GetMapping("/users")
    fun getAll() = repository.findAll().map { UserResponse(it.id!!, it.email) }

    @RequireAuth
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long) = repository.getOne(id).let { UserResponse(it.id!!, it.email) }
}