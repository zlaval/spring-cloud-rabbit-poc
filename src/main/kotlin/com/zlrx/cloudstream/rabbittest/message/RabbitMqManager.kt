package com.zlrx.cloudstream.rabbittest.message

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder

interface OutputChannelRegistration {

    @Output("outputChannel")
    fun outputChannel(): MessageChannel
}

interface RabbitMqProducer<in T> {
    fun produceMessage(msg: T)
}

@EnableBinding(OutputChannelRegistration::class)
class Producer @Autowired constructor(private val producer: OutputChannelRegistration) : RabbitMqProducer<String> {
    override fun produceMessage(msg: String) {
        val message = MessageBuilder.withPayload(msg)
            .setHeader("source_system", "zlrx")
            .build()
        producer.outputChannel().send(message)
    }
}

interface RabbitMqConsumer {
    companion object {
        const val INPUT = "inputChannel"
    }

    @Input(INPUT)
    fun inputChannel(): SubscribableChannel
}

@EnableBinding(RabbitMqConsumer::class)
class Consumer {

    private val logger = LoggerFactory.getLogger(javaClass)

    @StreamListener(RabbitMqConsumer.INPUT)
    fun consumeMessage(message: String) {
        logger.warn(message)
    }
}