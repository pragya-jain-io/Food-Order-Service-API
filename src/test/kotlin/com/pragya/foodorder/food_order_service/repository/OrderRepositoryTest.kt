package com.pragya.foodorder.food_order_service.repository

import com.pragya.foodorder.food_order_service.FoodOrderServiceApplication
import com.pragya.foodorder.food_order_service.model.Order
import com.pragya.foodorder.food_order_service.repository.OrderRepository
import com.pragya.foodorder.food_order_service.model.OrderStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import reactor.test.StepVerifier
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

//@SpringBootTest
//@SpringBootTest(classes = [FoodOrderServiceApplication::class])
//@ExtendWith(SpringExtension::class)
//@DataMongoTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderRepositoryTest {
    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun save_order(){
        val order = Order(
            id = null,
            customerName = "Customer1",
            items = listOf("Food1", "Food2"),
            totalPrice = 315.0,
            status = OrderStatus.PLACED,
            createdAt = Instant.now()
        )

        val savedOrder = orderRepository.save(order)

        StepVerifier.create(savedOrder)
            .assertNext{ saved->
                assertNotNull(saved.id)
                assertEquals("Customer1", saved.customerName)
                }
            . verifyComplete()
    }
}