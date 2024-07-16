package com.shoppy.cart.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.shoppy.cart.dto.ErrorDetails;
import com.shoppy.cart.dto.CartDTO;
import com.shoppy.cart.dto.CartItemDTO;
import com.shoppy.cart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/v1/carts")
@RestController
@Tag(name = "cart", description = "Methods of Cart Service")
public class CartController {
    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Operation(summary = "Get Cart", description = "Retrieve the cart for a specific user by user ID")
    @GetMapping("/{userId}")
    public CartDTO getCart(@PathVariable("userId") Integer userId) {
        logger.info("Retrieving cart for user ID: {}", userId);
        return cartService.getCart(userId);
    }

    @Operation(summary = "Add Item to Cart", description = "Add an item to the cart for a specific user")
    @PostMapping("/{userId}/items/{itemId}")
    public CartItemDTO addItemToCart(@PathVariable("userId") Integer userId, @PathVariable("itemId") Integer itemId) {
        logger.info("Adding item ID: {} to cart for user ID: {}", itemId, userId);
        return cartService.saveItem(userId, itemId);
    }

    @Operation(summary = "Remove Item from Cart", description = "Remove an item from the cart for a specific user")
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public String removeItemFromCart(@PathVariable("userId") Integer userId, @PathVariable("cartItemId") Integer cartItemId) {
        logger.info("Removing item ID: {} from cart for user ID: {}", cartItemId, userId);
        cartService.removeItem(userId, cartItemId);
        return "Item deleted from the cart";
    }

    @Operation(summary = "Update Item Quantity", description = "Update the quantity of an item in the cart for a specific user")
    @PutMapping("/{userId}/items/{itemId}")
    public CartItemDTO updateItemQuantity(@PathVariable("userId") Integer userId,
                                          @PathVariable("itemId") Integer itemId, @RequestParam("newQuantity") int newQuantity) {
        logger.info("Updating item ID: {} to quantity: {} for user ID: {}", itemId, newQuantity, userId);
        return cartService.updateItemQuantity(userId, itemId, newQuantity);
    }

    @Operation(summary = "Clear Cart", description = "Clear the cart for a specific user")
    @DeleteMapping("/{userId}")
    public String clearCart(@PathVariable("userId") Integer userId) {
        logger.info("Clearing cart for user ID: {}", userId);
        cartService.deleteCart(userId);
        return "Cart deleted";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
