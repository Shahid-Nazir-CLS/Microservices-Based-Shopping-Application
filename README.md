# Shopping Application - Microservices Architecture

## Overview

This project is a microservices-based shopping application developed using Spring Boot. The application is designed to handle user management, item catalog, shopping cart operations, order processing, and payment services. It leverages Kafka for communication between the cart and payment services to ensure reliable and asynchronous processing.

## Microservices

### 1. User Service
Handles user authentication, authorization, and profile management. Integrates with Google OAuth for login.

**Endpoints**:
- `/register` - Register a new user.
- `/login` - User login.
- `/profile` - Get user profile information.
- `/update-profile` - Update user profile.

### 2. Item Service
Manages the item catalog, including adding, updating, and retrieving items.

**Endpoints**:
- `/items` - Retrieve all items.
- `/items/{id}` - Retrieve item by ID.
- `/items/add` - Add a new item.
- `/items/update/{id}` - Update item details.

### 3. Cart Service
Handles the shopping cart operations, allowing users to add, remove, and view items in their cart. Includes order management functionality.

**Cart Endpoints**:
- `/cart` - Retrieve the current user's cart.
- `/cart/add` - Add an item to the cart.
- `/cart/remove/{itemId}` - Remove an item from the cart.
- `/cart/checkout` - Checkout the cart to create an order.

**Order Endpoints**:
- `/orders` - Retrieve all orders.
- `/orders/{id}` - Retrieve order by ID.
- `/orders/create` - Create a new order.

**Kafka Integration**:
- **Producer**: Sends a message to the Kafka topic upon order creation.
- **Listener**: Listens for payment status updates and updates the order status accordingly.

### 4. Payment Service
Handles payment processing and updates order status based on payment success or failure.

**Endpoints**:
- `/payments` - Retrieve all payments.
- `/payments/{id}` - Retrieve payment by ID.
- `/payments/process` - Process a payment.

**Kafka Integration**:
- **Listener**: Listens for payment requests from the cart service.
- **Producer**: Sends a message to a Kafka topic after processing the payment.
