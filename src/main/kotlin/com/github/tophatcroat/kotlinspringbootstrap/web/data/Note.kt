package com.github.tophatcroat.kotlinspringbootstrap.web.data

import java.time.Instant

class NoteRequest(
        val text: String = ""
)

class NoteResponse(
        val id: Long,
        val text: String,
        val user: UserResponse? = null,
        val createdAt: Instant
)
