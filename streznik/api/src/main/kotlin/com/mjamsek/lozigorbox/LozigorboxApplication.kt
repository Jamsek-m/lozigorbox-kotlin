package com.mjamsek.lozigorbox

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.mjamsek.lozigorbox"])
class LozigorboxApplication

fun main(args: Array<String>) {
    val ctx: ConfigurableApplicationContext = SpringApplication.run(LozigorboxApplication::class.java, *args)
}
