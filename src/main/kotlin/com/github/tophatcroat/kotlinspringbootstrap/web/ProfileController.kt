package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.ProfileRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Profile
import com.github.tophatcroat.kotlinspringbootstrap.exception.DoesNotExistException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.helpers.getUser
import com.github.tophatcroat.kotlinspringbootstrap.web.data.ProfileRequest
import com.github.tophatcroat.kotlinspringbootstrap.web.data.ProfileResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/{userId}/profile",
        consumes = ["*", MediaType.APPLICATION_JSON_UTF8_VALUE],
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class ProfileController(val repository: ProfileRepository) {

    @GetMapping
    fun get(@PathVariable userId: Long): ProfileResponse =
            if (repository.existsByUserId(userId))
                repository.findByUserId(userId).last().let { ProfileResponse(it.nick, it.image, it.createdAt) }
            else
                throw DoesNotExistException()

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun register(@Validated @RequestBody body: ProfileRequest, errors: BindingResult): ProfileResponse {
        errors.check()

        return repository.saveAndFlush(Profile(nick = body.nick, image = body.image, user = getUser())).let {
            ProfileResponse(it.nick, it.image, it.createdAt)
        }
    }
}
