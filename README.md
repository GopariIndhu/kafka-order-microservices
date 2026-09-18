Yes — add a root `README.md` so anyone opening the repository immediately understands the project.

Create this file here:

```text
kafka-order-microservices/
├── README.md
├── order-service/
├── inventory-service/
├── payment-service/
└── notification-service/
```

Use this content:

````markdown
# Kafka Order Processing Microservices

A Spring Boot microservices project demonstrating an event-driven order processing system using Apache Kafka and MySQL.

The system processes an order through multiple independent services:

- Order Service
- Inventory Service
- Payment Service
- Notification Service

Kafka topics are used to communicate asynchronously between services.

---

## Architecture

```text
Client / Postman
      |
      v
Order Service
      |
      v
orders-topic
      |
      v
Inventory Service
      |
      v
inventory-success-topic
      |
      v
Payment Service
      |
      v
payment-success-topic
      |
      v
Notification Service
````

---

## Services

### 1. Order Service

Port:

```text
8080
```

Responsibilities:

* Receives orders through REST API
* Converts the order into a Kafka event
* Publishes the event to `orders-topic`

Endpoint:

```http
POST /api/orders
```

Example request:

```json
{
  "orderId": 115,
  "product": "Mouse",
  "quantity": 2,
  "price": 1500
}
```

Example:

```text
POST http://localhost:8080/api/orders
```

---

### 2. Inventory Service

Port:

```text
8081
```

Consumes:

```text
orders-topic
```

Responsibilities:

* Receives new orders
* Checks product inventory in MySQL
* Reduces product stock
* Prevents duplicate stock reduction
* Publishes successful inventory events

Produces:

```text
inventory-success-topic
```

Example event:

```json
{
  "orderId": 115,
  "product": "Mouse",
  "quantity": 2,
  "price": 1500,
  "status": "INVENTORY_RESERVED"
}
```

Database:

```text
kafka_inventory
```

Important tables:

```text
products
inventory_processed_orders
```

---

### 3. Payment Service

Port:

```text
8082
```

Consumes:

```text
inventory-success-topic
```

Responsibilities:

* Processes payment only after inventory reservation succeeds
* Calculates the total amount
* Saves payment information into MySQL
* Prevents duplicate payments
* Publishes a payment success event

Produces:

```text
payment-success-topic
```

Example event:

```json
{
  "orderId": 115,
  "product": "Mouse",
  "amount": 3000,
  "status": "PAYMENT_SUCCESS"
}
```

Database:

```text
kafka_payment
```

Important table:

```text
payments
```

---

### 4. Notification Service

Port:

```text
8083
```

Consumes:

```text
payment-success-topic
```

Responsibilities:

* Sends an order confirmation after successful payment
* Prevents duplicate notifications
* Stores processed notification order IDs

Database:

```text
kafka_notification
```

Important table:

```text
processed_notifications
```

---

## Event Flow

For an order:

```json
{
  "orderId": 115,
  "product": "Mouse",
  "quantity": 2,
  "price": 1500
}
```

The event flow is:

```text
1. POST /api/orders

2. Order Service
   publishes Order
   ↓
   orders-topic

3. Inventory Service
   checks and reduces stock
   ↓
   inventory-success-topic

4. Payment Service
   processes and stores payment
   ↓
   payment-success-topic

5. Notification Service
   sends confirmation
```

---

## Idempotency

The project protects against duplicate Kafka events.

### Inventory Service

Before reducing stock, it checks whether the order has already been processed.

```text
inventory_processed_orders
```

This prevents stock from being reduced twice.

### Payment Service

The `orderId` is unique in the payments table.

This prevents duplicate payment records.

### Notification Service

Processed notification order IDs are stored in:

```text
processed_notifications
```

This prevents duplicate order confirmations.

Example duplicate event:

```text
Notification already sent for order 115
```

---

## Technologies

* Java 21
* Spring Boot
* Spring Kafka
* Apache Kafka
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Docker
* Postman
* IntelliJ IDEA

---

## Running Kafka

Kafka is running using Docker.

Start Kafka:

```bash
docker start kafka
```

Check the running container:

```bash
docker ps
```

Kafka broker:

```text
localhost:9092
```

---

## MySQL Databases

Create the databases:

```sql
CREATE DATABASE IF NOT EXISTS kafka_inventory;

CREATE DATABASE IF NOT EXISTS kafka_payment;

CREATE DATABASE IF NOT EXISTS kafka_notification;
```

---

## Environment Variables

Database passwords should not be committed to GitHub.

Use:

```properties
spring.datasource.password=${MYSQL_PASSWORD}
```

Set the environment variable locally:

```text
MYSQL_PASSWORD=your_mysql_password
```

---

## Running the Application

Start the services in this order:

```text
1. Kafka
2. Inventory Service
3. Payment Service
4. Notification Service
5. Order Service
```

Ports:

| Service              | Port |
| -------------------- | ---: |
| Order Service        | 8080 |
| Inventory Service    | 8081 |
| Payment Service      | 8082 |
| Notification Service | 8083 |

---

## Testing

Send a request using Postman:

```http
POST http://localhost:8080/api/orders
```

Body:

```json
{
  "orderId": 116,
  "product": "Mouse",
  "quantity": 2,
  "price": 1500
}
```

Expected flow:

```text
Order sent: 116

Inventory Service:
Stock updated

Payment Service:
Payment saved

Notification Service:
Sending confirmation for order 116
```


## Future Improvements

Possible enhancements:

* Inventory failure events
* Payment failure events
* Dead Letter Topics
* Kafka retries
* Docker Compose for all services
* API Gateway
* Service discovery
* Centralized logging
* Distributed tracing
* Email notification integration
* Order status tracking



This README is strong enough for a portfolio repository because it explains the architecture, services, Kafka topics, databases, testing flow, and idempotency rather than only showing code.
