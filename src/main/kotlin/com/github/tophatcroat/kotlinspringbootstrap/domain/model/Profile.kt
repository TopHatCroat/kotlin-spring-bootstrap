package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import java.time.Instant
import javax.persistence.*

@Entity(name = "profiles")
data class Profile(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        var nick: String = "",
        var image: String = "",
        @ManyToOne(targetEntity = User::class)
        var user: User? = null,
        var createdAt: Instant = Instant.now()
)
