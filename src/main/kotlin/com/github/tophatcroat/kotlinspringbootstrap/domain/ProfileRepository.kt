package com.github.tophatcroat.kotlinspringbootstrap.domain

import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Profile
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findFirstByUserOrderByCreatedAtDesc(user: User): Profile?

    fun findFirstByUser(user: User): Profile?

    fun findByUserId(userId: Long): List<Profile>

    fun existsByUserId(userId: Long): Boolean
}
