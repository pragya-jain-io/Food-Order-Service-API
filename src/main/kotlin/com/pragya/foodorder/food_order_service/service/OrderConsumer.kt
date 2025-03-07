package com.pragya.foodorder.food_order_service.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class OrderConsumer {

    @KafkaListener(topics = ["order_updates"], groupId = "order-consumer-group")
    fun consumeOrderUpdate(message: org.apache.kafka.clients.consumer.ConsumerRecord<String, String>) {
        val orderId = message.key()
        val status = message.value()
        println("Kafka Message: $message")
        println("Order Update Received - ID: $orderId, Status: $status")
    }


}