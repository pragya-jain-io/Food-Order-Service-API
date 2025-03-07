package com.pragya.foodorder.food_order_service.service

//import com.pragya.foodorder.food_order_service.service.OrderProducer
import com.pragya.foodorder.food_order_service.model.Order
import com.pragya.foodorder.food_order_service.model.OrderStatus
import com.pragya.foodorder.food_order_service.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class OrderService(private val orderRepository: OrderRepository, private val orderProducer: OrderProducer ) {

    fun placeOrder(order: Order): Mono<Order>{
        val newOrder = order.copy(
            id = null,
            status = OrderStatus.PLACED,
            createdAt = Instant.now()
        )
        return orderRepository.save(order)
    }

    fun getOrderById(id: String): Mono<Order> {
        return orderRepository.findById(id)
    }

    fun getAllOrders(): Flux<Order> {
        return orderRepository.findAll()
    }

    fun updateOrderStatus(id: String, status: OrderStatus): Mono<Order> {
        return orderRepository.findById(id)
            .flatMap { order ->
                order.status = status
                orderRepository.save(order)
                    .doOnSuccess { order -> orderProducer.sendOrderUpdate(order.id!!, order.status.name) }
            }
    }

}