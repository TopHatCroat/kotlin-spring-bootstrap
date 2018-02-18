package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Profile(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val username: String = ""
)