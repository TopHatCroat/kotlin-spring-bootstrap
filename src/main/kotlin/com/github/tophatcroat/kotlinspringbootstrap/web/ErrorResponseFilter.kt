package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.web.data.InvalidDataResponse
import com.google.gson.GsonBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ErrorResponseFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        try {
            filterChain.doFilter(request, response)
        } catch (e: Throwable) {

            var message: String? = request.getAttribute("javax.servlet.error.message") as String?
            if(message.isNullOrBlank())
                message = e.message
            if(message.isNullOrBlank())
                message = "Something bad happened"

            val statusCode = request.getAttribute("javax.servlet.error.status_code") as Int? ?: 500

            val responseEntity = InvalidDataResponse(Date(), statusCode, message!!, emptyMap())

            response.status = statusCode
            response.writer.write(GsonBuilder().create().toJson(responseEntity))
        }
    }
}
