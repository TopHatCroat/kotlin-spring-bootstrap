package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users", consumes = ["*", MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController(val repository: UserRepository) {
    @GetMapping
    fun getAll() = repository.findAll().map { UserResponse(it.id!!, it.email, it.createdAt) }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = repository.getOne(id).let { UserResponse(it.id!!, it.email, it.createdAt) }
}
