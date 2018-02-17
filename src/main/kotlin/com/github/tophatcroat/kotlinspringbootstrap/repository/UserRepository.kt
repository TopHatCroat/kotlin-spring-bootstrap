package com.github.tophatcroat.kotlinspringbootstrap.repository

import com.github.tophatcroat.kotlinspringbootstrap.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByName(name: String): Boolean

    fun existsByNameAndPassword(name: String, password: String): Boolean

    fun findByName(name: String): User?

    fun findByToken(name: String): User?

    fun findByNameAndPassword(name: String, password: String): User?
}