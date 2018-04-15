package com.github.tophatcroat.kotlinspringbootstrap.web.data

import com.google.gson.annotations.SerializedName
import io.swagger.annotations.ApiModel
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@ApiModel(description = "Contains login credentials used to register to the API")
data class UserLoginRequest(
        @field:NotEmpty(message = "can't be missing")
        @field:Size(min = 1, message = "can't be empty")
        @field:Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$", message = "not valid email")
        val email: String?,

        @field:NotEmpty(message = "can't be missing")
        @field:Size(min = 1, message = "can't be empty")
        val password: String?
)

data class UserLoginResponse(
        val id: Long,
        @field:SerializedName("email")
        val email: String,
        @field:SerializedName("token")
        val token: String
)


data class UserResponse(
        val id: Long,
        @field:SerializedName("email")
        val email: String
)

