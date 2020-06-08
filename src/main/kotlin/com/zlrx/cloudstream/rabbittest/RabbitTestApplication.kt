package com.zlrx.cloudstream.rabbittest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitTestApplication

fun main(args: Array<String>) {
    runApplication<RabbitTestApplication>(*args)
}
