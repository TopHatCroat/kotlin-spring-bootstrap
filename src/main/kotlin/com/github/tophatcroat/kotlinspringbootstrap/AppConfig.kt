package com.github.tophatcroat.kotlinspringbootstrap

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("com.github.tophatcroat.kotlinspringbootstrap") // to enable autowire
public class AppConfig {}