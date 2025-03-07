package com.pragya.foodorder.food_order_service.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class OrderProducer(private val kafkaTemplate: KafkaTemplate<String,String>) {

    fun sendOrderUpdate(orderId: String, status: String) {
        kafkaTemplate.send("order_updates", orderId, status)
    }

}