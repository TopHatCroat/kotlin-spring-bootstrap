package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import com.github.tophatcroat.kotlinspringbootstrap.web.data.InvalidDataResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletResponse

@ControllerAdvice()
class GlobalControllerAdvice {
    @ExceptionHandler(InvalidDataException::class)
    fun handleInvalidDataException(exception: InvalidDataException, httpServletResponse: HttpServletResponse): ResponseEntity<InvalidDataResponse> {
        httpServletResponse.status = HttpStatus.BAD_REQUEST.value()

        return ResponseEntity<InvalidDataResponse>(InvalidDataResponse(
                Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request data",
                exception.errors.fieldErrors.map { it.field to (it.defaultMessage ?: "") }.toMap()
        ), HttpStatus.NOT_FOUND)
    }
}
