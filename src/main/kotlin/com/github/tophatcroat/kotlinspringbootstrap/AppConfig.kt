package com.github.tophatcroat.kotlinspringbootstrap

import com.github.tophatcroat.kotlinspringbootstrap.exception.InvalidDataException
import com.github.tophatcroat.kotlinspringbootstrap.web.data.InvalidDataResponse
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletResponse


@Configuration
@ComponentScan("com.github.tophatcroat.kotlinspringbootstrap") // to enable autowire
public class AppConfig {}