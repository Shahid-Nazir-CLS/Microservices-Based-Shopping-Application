package com.shoppy.order.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.shoppy.order.dto.ErrorDetails;
import com.shoppy.order.dto.OrderDTO;
import com.shoppy.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "order", description = "Methods of Order Service")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get Order", description = "Retrieve an order by its ID")
    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable("orderId") int orderId) {
        logger.info("Retrieving order with ID: {}", orderId);
        return orderService.getOrder(orderId);
    }

    @Operation(summary = "Get Orders by Customer ID", description = "Retrieve all orders for a specific customer by customer ID")
    @GetMapping("/customer/{customerId}")
    public List<OrderDTO> getOrdersByCustomerId(@PathVariable("customerId") int customerId) {
        logger.info("Retrieving orders for customer ID: {}", customerId);
        return orderService.getOrdersByCustomerId(customerId);
    }

    @Operation(summary = "Cancel Order", description = "Cancel an order by its ID")
    @PutMapping("/cancel/{orderId}")
    public OrderDTO cancelOrder(@PathVariable("orderId") int orderId) throws Exception {
        logger.info("Cancelling order with ID: {}", orderId);
        return orderService.cancelOrder(orderId);
    }

    @Operation(summary = "Create Order", description = "Create a new order for a specific user by user ID")
    @PostMapping("/{userId}")
    public OrderDTO createOrder(@PathVariable("userId") int userId) throws Exception {
        try {
            logger.info("Creating order for user ID: {}", userId);
            OrderDTO orderDTO = orderService.createOrder(userId);
            logger.info("Order created successfully: {}", orderDTO);
            return orderDTO;
        } catch (Exception e) {
            logger.error("Error creating order for user ID: {}", userId, e);
            throw new Exception(e);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}