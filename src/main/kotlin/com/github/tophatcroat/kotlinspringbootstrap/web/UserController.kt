package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.ProfileRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.exception.DoesNotExistException
import com.github.tophatcroat.kotlinspringbootstrap.exception.WtfException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.getUser
import com.github.tophatcroat.kotlinspringbootstrap.web.data.ProfileResponse
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users", consumes = ["*", MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserController(val repository: UserRepository,
                     val profileRepository: ProfileRepository) {
    @GetMapping
    fun getAll() = repository.findAll().map { UserResponse(it.id!!, it.email) }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): UserResponse {
        val user = if (repository.existsById(id)) repository.getOne(id) else throw DoesNotExistException()

        val profile = profileRepository.findFirstByUserOrderByCreatedAtDesc(user) ?: throw WtfException()

        return UserResponse(user.id!!, user.email, ProfileResponse(profile.nick, profile.image, profile.createdAt))
    }

    @GetMapping("/me")
    fun getMe(): UserResponse {
        val user = getUser()

        val profile = profileRepository.findFirstByUserOrderByCreatedAtDesc(user) ?: throw WtfException()

        return UserResponse(user.id!!, user.email, ProfileResponse(profile.nick, profile.image, profile.createdAt))
    }

}
