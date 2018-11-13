package com.github.tophatcroat.kotlinspringbootstrap.service

import com.github.tophatcroat.kotlinspringbootstrap.domain.ProfileRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Profile
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
@Transactional
class ProfileService(val repository: ProfileRepository) {

    fun createProfileForUser(user: User, nick: String, imageUrl: String) = repository.saveAndFlush(Profile(
            null, nick, imageUrl, user, Instant.now()))

}
