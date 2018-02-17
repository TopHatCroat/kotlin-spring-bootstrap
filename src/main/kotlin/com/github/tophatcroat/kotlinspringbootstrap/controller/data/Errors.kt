package com.github.tophatcroat.kotlinspringbootstrap.controller.data

import java.util.*

data class InvalidDataResponse(
        val timestamp: Date,
        val status: Int,
        val error: String,
        val messages: Map<String, String>
)