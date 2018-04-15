package com.github.tophatcroat.kotlinspringbootstrap

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
@EnableAutoConfiguration(exclude = [(JacksonAutoConfiguration::class)])
class Application

fun main(args: Array<String>) {
//    SpringApplication.run(Application::class.java, *args)
    SpringApplicationBuilder()
            .sources(Application::class.java)
            .run(*args)
}