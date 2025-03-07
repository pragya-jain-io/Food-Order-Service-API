package com.pragya.foodorder.food_order_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = ["com.pragya.foodorder.food_order_service.repository"])


class FoodOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<FoodOrderServiceApplication>(*args)
}
