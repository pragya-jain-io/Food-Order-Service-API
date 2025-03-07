package com.pragya.foodorder.food_order_service.controller

import com.pragya.foodorder.food_order_service.model.Order
import com.pragya.foodorder.food_order_service.model.OrderStatus
import com.pragya.foodorder.food_order_service.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono



@RestController
@RequestMapping("/orders")
@Validated
class OrderController(

    val orderService: OrderService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody order: Order): Mono<Order> {
        println("Received Order: $order")
        return orderService.placeOrder(order)

    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: String): Mono<ResponseEntity<Order>> {
        return orderService.getOrderById(id)
            .map { order -> ResponseEntity.ok(order) }  // Return 200 OK with the order
            .defaultIfEmpty(ResponseEntity.notFound().build())  // Return 404 if not found
    }

    @GetMapping
    fun getAllOrders(): Flux<Order> {
        return orderService.getAllOrders()
    }

    @PutMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: String,
        @RequestParam status: OrderStatus
    ): Mono<Order> {
        return orderService.updateOrderStatus(id, status)
    }
}