package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import java.time.Instant
import javax.persistence.*

@Entity(name = "profiles")
data class Profile(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val username: String = "",
        val image: String = "",
        @ManyToOne(targetEntity = User::class)
        val userId: Long,
        val createdAt: Instant
)