package com.github.tophatcroat.kotlinspringbootstrap.web.data

import java.util.*

data class InvalidDataResponse(
        val timestamp: Date,
        val status: Int,
        val error: String,
        val messages: Map<String, String>
)