package com.zlrx.cloudstream.rabbittest.controller

import com.zlrx.cloudstream.rabbittest.message.Producer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DataProducerController(
    val producer: Producer
) {

    @GetMapping("/")
    fun insert(): Mono<String> {
        for (i in 1..10) {
            producer.produceMessage("Message $i")
        }
        return Mono.just("10 messages were sent to the queue")
    }
}