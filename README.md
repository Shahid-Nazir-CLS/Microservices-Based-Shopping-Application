# Shopping App

## Introduction

This shopping app is a pet project designed to showcase and implement skills in Spring and microservices. The application employs a modular architecture, utilizing various services and technologies to create a scalable and efficient online shopping platform. The architecture integrates a frontend client with backend services and incorporates modern practices such as authentication, session management, and asynchronous communication.

## Architecture Components

### 1. Frontend Client
**Description:** A web application using JSP views that interacts with all backend services.
- **Authentication:** Implements login functionality using Google OAuth2, managed by the Customer Service.
- **Session Management:** Uses JDBC for session management to keep users logged in across sessions.
- **Security:** Frontend endpoints are secured using Spring Security.

### 2. Microservices

#### Item Service
- **Purpose:** Manages the inventory and details of items available in the shopping app.
- **Functions:** Handles operations such as retrieving item details, updating stock levels, and managing item information.

#### Cart Service
- **Purpose:** Manages the shopping cart functionality for users.
- **Functions:** Handles operations like adding items to the cart, updating cart items, and managing cart state.

#### Order Service
- **Purpose:** Handles order processing and tracking.
- **Functions:** Manages order creation, status updates, and communicates with the Payment Service via Kafka.
- **Kafka Integration:** Sends order messages to Kafka, listens for payment confirmation messages from Kafka.

#### Payment Service
- **Purpose:** Simulates a third-party payment app to handle payment processing.
- **Functions:** Processes payment requests, waits for a simulated payment validation period (e.g., 5 seconds), and sends back payment status updates to Kafka.

#### Customer Service
- **Purpose:** Manages customer-related data including profiles, addresses, and authentication.
- **Functions:** Handles user registration, login, profile updates, and manages customer data.
- **Authentication:** Uses Spring Security and OAuth2 to authenticate users and create sessions.

### 3. Kafka (Message Queue)
- **Purpose:** Facilitates asynchronous communication between the Order Service and the Payment Service.
- **Flow:**
  - Order Service sends payment-related messages to Kafka.
  - Payment Service listens to Kafka, processes payment messages, and sends status updates back.

### 4. Service Discovery and Configuration

#### API Gateway (Spring Cloud Gateway)
- **Purpose:** Acts as a single entry point for all client requests.
- **Functions:** Handles routing, load balancing, authentication, and other cross-cutting concerns.
- **Flow:** Routes requests from the frontend client to the appropriate microservices based on the request path.

#### Eureka Server
- **Purpose:** Provides service discovery capabilities.
- **Functions:** Allows microservices to register themselves and discover other services for load balancing and routing.
- **Flow:** Microservices register with Eureka, and the API Gateway uses Eureka to discover service instances.

#### Config Server
- **Purpose:** Centralizes configuration management for all microservices.
- **Functions:** Provides configuration properties to microservices from a centralized GitHub repository.

## Interaction Flow

1. **Client Interaction:**
   - A user accesses the frontend client and sends a request to the API Gateway.
   - The API Gateway routes requests to the appropriate microservice.

2. **Authentication and Session Management:**
   - When a user logs in via the frontend client, the request is sent to the Customer Service.
   - The Customer Service authenticates the user using Google OAuth2 and creates a session.
   - The session is managed using JDBC to ensure users remain logged in across visits.

3. **Order Processing:**
   - When a user places an order by clicking the "buy items" button, the request is sent to Kafka.
   - The Order Service listens for new orders from Kafka, processes them, and sends payment requests to Kafka.
   - The Payment Service listens to Kafka, processes payment messages, waits for a simulated period, and sends payment confirmation back.
   - The Order Service updates the order status to "successful" upon receiving the payment confirmation.

4. **Service Discovery and Configuration:**
   - Microservices register with the Eureka Server for dynamic discovery.
   - The API Gateway uses Eureka to route requests to the correct microservice instances.
   - Microservices pull their configuration from the Config Server, which retrieves configurations from a GitHub repository.

## Summary

This architecture effectively utilizes a microservices approach to manage different aspects of the shopping application, incorporating modern technologies such as Kafka for messaging, OAuth2 for authentication, and Eureka for service discovery. The addition of an API Gateway and a Config Server enhances the scalability and maintainability of the system.

### Services

- **Shoppy (Frontend Client)**
  - **Purpose:** Acts as the web interface for users.
  - **Port:** `8080`

- **Item Service (Inventory Management)**
  - **Purpose:** Manages items and inventory.
  - **Port:** `8081`

- **Customer Service (Authentication, Customer Data, Address Management)**
  - **Purpose:** Handles customer profiles, authentication, and addresses.
  - **Port:** `8082`

- **Cart Service (Cart Functionality)**
  - **Purpose:** Manages the shopping cart operations.
  - **Port:** `8083`

- **Order Service (Order Management)**
  - **Purpose:** Manages order creation, updates, and status.
  - **Port:** `8085`

- **Payment Service (Payment Simulation via Kafka)**
  - **Purpose:** Simulates third-party payment processing.
  - **Port:** `8084`

### Additional Components

- **Eureka Server (Service Discovery)**
  - **Purpose:** Provides service discovery capabilities.
  - **Port:** `8761`

- **Config Server (Configuration Management)**
  - **Purpose:** Centralizes configuration management for all microservices.
  - **Port:** `8087`

- **API Gateway (Routing and Load Balancing)**
  - **Purpose:** Routes requests to the appropriate microservices.
  - **Port:** `9191`

- **Zipkin (Distributed Tracing)**
  - **Purpose:** Provides distributed tracing for monitoring and debugging.
  - **Port:** `9411`

- **Kafka Broker**
  - **Purpose:** Facilitates asynchronous communication between services.
  - **Port:** `9042`

## Getting Started

### Prerequisites

Ensure the following components are installed and configured:
- Apache Kafka
- Zookeeper
- Zipkin
- Java (for running Zipkin)

### Starting Services

1. **Start Zookeeper**

   Open a terminal and run:
   ```bash
   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   
2. **Start Kafka**

    Launch Kafka broker in a separate terminal:
   ```bash
   .\bin\windows\kafka-server-start.bat .\config\server.properties

      
3. **Start Zipkin**

   Initiate Zipkin for distributed tracing:
   ```bash
   java -jar zipkin-server-3.4.0-exec.jar

  **Zipkin Dashboard**
    Access the Zipkin dashboard at http://127.0.0.1:9411/
   
4. **Run Services**
- Open separate VS Code windows for the shoppy (frontend client) and customer services.
- Open another VS Code window for all other services (Item, Cart, Order, Payment).

Monitor Services: Ensure Zipkin, Zookeeper, and Kafka are running alongside your VS Code instances.
