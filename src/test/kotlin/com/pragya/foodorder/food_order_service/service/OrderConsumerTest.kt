package com.pragya.foodorder.food_order_service.service

import com.pragya.foodorder.food_order_service.model.OrderStatus
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka

import org.springframework.test.context.junit.jupiter.SpringExtension

import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import java.util.*
import kotlin.test.assertNotNull

@ExtendWith(SpringExtension::class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["order_updates"])
class OrderConsumerTest {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Test
    fun orderUpdatesToKafkaConsumer() {
        val orderId = "12345"
        val status = OrderStatus.PREPARING.toString()

        // Send message to Kafka
        kafkaTemplate.send("order_updates", orderId, status)

        // Manually create a Kafka consumer to verify message reception
        val consumerProps = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        }

        KafkaConsumer<String, String>(consumerProps).use { consumer ->
            consumer.subscribe(listOf("order_updates"))
            val records = consumer.poll(Duration.ofSeconds(10))

            // Ensure we received at least one record
            val record: ConsumerRecord<String, String>? = records.firstOrNull()
            if (record != null) {
                println(record.key())
                assertNotNull(record.key())
            }
            assertEquals(status, record?.value(), "Order status should match")
        }
}}