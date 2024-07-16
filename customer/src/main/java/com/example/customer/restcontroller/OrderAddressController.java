package com.example.customer.restcontroller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.customer.dto.ErrorDetails;
import com.example.customer.dto.OrderAddressDTO;
import com.example.customer.service.OrderAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/orderaddresses")
@Tag(name = "orderAddress", description = "Methods of Order Address Service")
public class OrderAddressController {

    private static final Logger logger = LogManager.getLogger(OrderAddressController.class);

    @Autowired
    private OrderAddressService orderAddressService;

    @Operation(summary = "Get Order Address by ID", description = "Retrieve an order address by its ID")
    @GetMapping("/{addressId}")
    public OrderAddressDTO getAddress(@PathVariable("addressId") int addressId) {
        logger.info("Retrieving order address with ID: {}", addressId);
        return orderAddressService.getOrderAddressById(addressId);
    }

    @Operation(summary = "Create Order Address", description = "Create a new order address")
    @PostMapping("/")
    public OrderAddressDTO createOrderAddress(@RequestBody OrderAddressDTO orderAddressDto) {
        logger.info("Creating new order address: {}", orderAddressDto);
        return orderAddressService.saveOrderAddress(orderAddressDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
