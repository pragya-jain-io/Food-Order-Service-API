package com.pragya.foodorder.food_order_service.repository

import com.pragya.foodorder.food_order_service.model.Order
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: ReactiveMongoRepository<Order, String> {
}