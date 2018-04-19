package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.web.data.InvalidDataResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*


@RestController
class IndexController : ErrorController {

    @GetMapping(consumes = ["*", MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseStatus(value = HttpStatus.FOUND)
    fun home() = ResponseEntity("", HttpHeaders().apply {
        location = URI("/swagger-ui.html")
    }, HttpStatus.FOUND)

    @GetMapping("/ping")
    @ResponseStatus(value = HttpStatus.OK)
    fun ping() = "pong"

    @RequestMapping(value = PATH)
    fun error() = ResponseEntity(InvalidDataResponse(
            Date(),
            HttpStatus.NOT_FOUND.value(),
            "You took a wrong turn there, mate", emptyMap()),
            HttpStatus.NOT_FOUND)

    override fun getErrorPath(): String {
        return PATH
    }

    companion object {
        private const val PATH = "/error"
    }
}