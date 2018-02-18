package com.github.tophatcroat.kotlinspringbootstrap.domain

import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndPassword(email: String, password: String): Boolean

    fun findByEmail(email: String): User?

    fun findByToken(token: String): User?

    fun findByEmailAndPassword(email: String, password: String): User?
}