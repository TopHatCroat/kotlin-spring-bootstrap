package com.github.tophatcroat.kotlinspringbootstrap

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
@EnableAutoConfiguration(exclude = [(JacksonAutoConfiguration::class)])
class Application : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600)
        super.addCorsMappings(registry)
    }
}

fun main(args: Array<String>) {
//    SpringApplication.run(Application::class.java, *args)
    SpringApplicationBuilder()
            .sources(Application::class.java)
            .run(*args)
}