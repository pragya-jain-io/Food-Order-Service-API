package com.pragya.foodorder.food_order_service.model

import org.springframework.data.annotation.Id
import java.time.Instant

data class Order(
    @Id
    var id: String? = null,
    var customerName: String,
    var items: List<String>,
    var totalPrice: Double,
    var status: OrderStatus,
    val createdAt: Instant = Instant.now()
)
