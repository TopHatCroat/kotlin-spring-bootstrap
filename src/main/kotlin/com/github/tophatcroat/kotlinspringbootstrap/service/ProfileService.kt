package com.github.tophatcroat.kotlinspringbootstrap.service

import com.github.tophatcroat.kotlinspringbootstrap.domain.ProfileRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Profile
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
@Transactional
class ProfileService(val repository: ProfileRepository) {

    fun createProfileForUser(userId: Long, name: String, imageUrl: String) = repository.saveAndFlush(Profile(
            null, name, imageUrl, userId, Instant.now()))


}