# Food Order Service API

This project is a real-time food order processing system built using Kotlin, Spring Boot (WebFlux), MongoDB, and Kafka.

## Tech Stack
- **Backend:** Kotlin, Spring Boot (WebFlux)
- **Database:** MongoDB
- **Messaging:** Kafka
- **Build Tool:** Gradle
- **Testing:** JUnit5, Mockito, Testcontainers


## Features
- Order placement with MongoDB persistence
- Order status updates with Kafka messaging
- Reactive programming with WebFlux
- Unit tests using JUnit5 and Testcontainers

## Endpoints

| Method | Endpoint                | Description                      |
|---------|------------------------|----------------------------------|
| POST     | `/orders`               | Create a new order               |
| GET      | `/orders/{id}`          | Get order details by ID          |
| GET      | `/orders`               | Get all orders                   |
| PUT      | `/orders/{id}/status`   | Update order status              |


