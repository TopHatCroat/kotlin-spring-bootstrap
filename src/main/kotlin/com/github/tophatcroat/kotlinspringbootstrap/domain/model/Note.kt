package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import java.time.Instant
import javax.persistence.*

@Entity(name = "notes")
class Note(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        var text: String = "",
        @ManyToOne(targetEntity = User::class)
        var user: User? = null,
        var createdAt: Instant = Instant.now()
)
