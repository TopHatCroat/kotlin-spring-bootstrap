package com.github.tophatcroat.kotlinspringbootstrap

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSerializer
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans
import org.springframework.http.converter.json.GsonHttpMessageConverter
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.json.Json
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.ApiKeyVehicle
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.service.ApiKey



@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = [(JacksonAutoConfiguration::class)])
class Application

fun main(args: Array<String>) {
//    SpringApplication.run(Application::class.java, *args)
    SpringApplicationBuilder()
            .sources(Application::class.java)
            .initializers(beans {
                bean {
                    SecurityConfiguration(null, null, null, null, "Bearer ", ApiKeyVehicle.HEADER,
                            "Authorization", ",")
                }
                bean {
                    Docket(DocumentationType.SWAGGER_2)
                            .securityContexts(listOf(SecurityContext.builder()
                                    .securityReferences(
                                            listOf(SecurityReference("api_key", arrayOf(
                                                    AuthorizationScope("global", "accessEverything")))
                                            )
                                    )
                                    .forPaths(PathSelectors.any())
                                    .build()))
                            .securitySchemes(listOf(ApiKey("Authorization", "api_key", "header")))
                            .apiInfo(ApiInfoBuilder()
                                    .title("Do You Even Code")
                                    .description("Spring Boot bootstrap project")
                                    .version("0.1")
                                    .contact(Contact("Antonio Martinović", "www.example.com", "antmartin2@foi.hr"))
                                    .license("Unlicense")
                                    .build())
                            .select()
                            .apis(RequestHandlerSelectors.basePackage("com.github.tophatcroat.kotlinspringbootstrap.web"))
                            .paths(PathSelectors.any())
                            .build()
                }

                bean {
                    GsonHttpMessageConverter().apply {
                        gson = GsonBuilder()
                                .registerTypeAdapter(Json::class.java, JsonSerializer<Json> { src, _, _ ->
                                    JsonParser().parse(src.value())
                                }).create()
                    }
                }
            })
            .run(*args)
}