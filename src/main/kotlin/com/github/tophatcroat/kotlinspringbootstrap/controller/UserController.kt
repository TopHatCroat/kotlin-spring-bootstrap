package com.github.tophatcroat.kotlinspringbootstrap.controller

import com.github.tophatcroat.kotlinspringbootstrap.exceptions.UserExistsException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.model.User
import com.github.tophatcroat.kotlinspringbootstrap.model.UserLoginJson
import com.github.tophatcroat.kotlinspringbootstrap.repository.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController class UserController(val repository: UserRepository,
                                     val userService: UserService) {

    @PostMapping("/user")
    fun register(@Valid @RequestBody login: UserLoginJson, errors: Errors): Any {
        errors.check()

        if(repository.existsByName(login.name))
            throw UserExistsException()

        return repository.save(User(null, login.name, login.password))
    }

    @GetMapping("/user")
    fun getAll(): List<UserLoginJson> {
        return repository.findAll().map {
            UserLoginJson(it.name, it.password)
        }
    }
}