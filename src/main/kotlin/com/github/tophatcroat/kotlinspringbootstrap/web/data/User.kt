package com.github.tophatcroat.kotlinspringbootstrap.web.data

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UserLoginRequest(
        @field:NotEmpty(message = "can't be missing")
        @field:Size(min = 1, message = "can't be empty")
        @field:Pattern(regexp = "^\\w+$", message = "must be alphanumeric")
        val email: String?,

        @field:NotEmpty(message = "can't be missing")
        @field:Size(min = 1, message = "can't be empty")
        val password: String?
)

data class UserLoginResponse(
        @field:SerializedName("email")
        val email: String?,
        @field:SerializedName("token")
        val token: String?
)

