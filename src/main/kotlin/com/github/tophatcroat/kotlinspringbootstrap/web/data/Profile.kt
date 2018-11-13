package com.github.tophatcroat.kotlinspringbootstrap.web.data

import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class ProfileResponse(
        val nick: String = "",
        val image: String = "",
        val createdAt: Instant
)

class ProfileRequest(
        @field:NotEmpty(message = "can't be missing")
        @field:Size(min = 3, message = "must be at least 3 characters long")
        val nick: String = "",
        @field:Pattern(regexp = "(^http(s)://.*(\\.png|\\.jpg))?", message = "not valid email")
        val image: String = ""
)
