package com.github.tophatcroat.kotlinspringbootstrap

import com.google.gson.Gson
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.GsonHttpMessageConverter


@SpringBootApplication
@EnableAutoConfiguration(exclude = [(JacksonAutoConfiguration::class)])
class Application
//    : WebMvcConfigurerAdapter() {
//
//    override fun addCorsMappings(registry: CorsRegistry?) {
//        registry!!.addMapping("/api/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(false)
//                .maxAge(3600)
//        super.addCorsMappings(registry)
//    }
//}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@Configuration
@ConditionalOnClass(Gson::class)
@ConditionalOnMissingClass("com.fasterxml.jackson.core.JsonGenerator")
@ConditionalOnBean(Gson::class)
class GsonHttpMessageConverterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun gsonHttpMessageConverter(gson: Gson): GsonHttpMessageConverter {
        val converter = GsonHttpMessageConverter()
        converter.gson = gson
        return converter
    }

}