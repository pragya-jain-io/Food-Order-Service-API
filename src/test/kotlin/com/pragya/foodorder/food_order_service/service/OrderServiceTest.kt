    package com.pragya.foodorder.food_order_service.service

    import com.pragya.foodorder.food_order_service.model.Order
    import com.pragya.foodorder.food_order_service.model.OrderStatus
    import com.pragya.foodorder.food_order_service.repository.OrderRepository
    import org.junit.jupiter.api.Assertions.assertEquals
    import org.junit.jupiter.api.extension.ExtendWith
    import org.mockito.Mockito.mock
    import org.springframework.beans.factory.annotation.Autowired
    import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
    import org.springframework.boot.test.context.SpringBootTest
    import org.springframework.test.context.junit.jupiter.SpringExtension
    import reactor.test.StepVerifier
    import java.time.Instant
    import kotlin.test.Test
    import kotlin.test.assertNotNull


    @ExtendWith(SpringExtension::class)
    @SpringBootTest
    class OrderServiceTest @Autowired constructor(
        private val orderRepository: OrderRepository,
        private val orderProducer: OrderProducer
    ) {
        private val orderService = OrderService(orderRepository,orderProducer)

        @Test
        fun returnMono() {
            val order = Order(
                id = null,
                customerName = "Customer1",
                items = listOf("Food1", "Food2"),
                totalPrice = 315.0,
                status = OrderStatus.PLACED,
                createdAt = Instant.now()
            )

            val result = orderService.placeOrder(order)

            StepVerifier.create(result)
                .assertNext { savedOrder ->
                    assertNotNull(savedOrder.id) // MongoDB should generate an ID
                    println("Generated Order ID: ${savedOrder.id}") // Debugging
                    assertEquals(OrderStatus.PLACED, savedOrder.status) // Ensuring override
                }
                .verifyComplete()
        }


        @Test
        fun to_kafka(){
            val order = Order(
                    id = null,
                    customerName = "Customer1",
                    items = listOf("Food1", "Food2"),
                    totalPrice = 315.0,
                    status = OrderStatus.PLACED,
                    createdAt = Instant.now()
            )
            val result = orderService.placeOrder(order)

            StepVerifier.create(result)
                .assertNext{
                    savedOrder ->
                    assertNotNull(savedOrder.id)
                    assertEquals(OrderStatus.PLACED, savedOrder.status)

                    orderProducer.sendOrderUpdate(savedOrder.id!!, savedOrder.status.toString())
                }
                .verifyComplete()

            }




    }

