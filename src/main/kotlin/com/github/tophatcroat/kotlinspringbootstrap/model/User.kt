package com.github.tophatcroat.kotlinspringbootstrap.model

import com.google.gson.annotations.SerializedName
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity(name = "users")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val name: String,
        val password: String
)

data class UserLoginJson(
        @SerializedName("username")
        @NotNull(message = "can't be missing")
        @Size(min = 1, message = "can't be empty")
        @Pattern(regexp = "^\\w+$", message = "must be alphanumeric")
        val name: String,

        @SerializedName("password")
        @NotNull(message = "can't be missing")
        @Size(min = 1, message = "can't be empty")
        val password: String
)

