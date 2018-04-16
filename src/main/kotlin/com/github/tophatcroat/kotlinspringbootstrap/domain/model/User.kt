package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        var email: String = "",
        var password: String = "",
        var token: String = "",
        var createdAt: Instant = Instant.now()
)