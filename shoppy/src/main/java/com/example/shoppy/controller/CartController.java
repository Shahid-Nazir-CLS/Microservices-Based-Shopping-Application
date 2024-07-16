package com.example.shoppy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.shoppy.dto.AddressDTO;
import com.example.shoppy.dto.CartDTO;
import com.example.shoppy.dto.CustomerDTO;
import com.example.shoppy.service.CartService;
import com.example.shoppy.service.CustomerService;

@Controller
public class CartController {
    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/cart")
    public String getCart(Model model) {
        int userId = 1; // Example, replace with actual user identification logic
        logger.info("Fetching cart details for user ID: " + userId);

        CartDTO cartDTO = cartService.getUserCart(userId);
        if (cartDTO != null) {
            CustomerDTO customerDTO = customerService.getCustomer(userId);
            AddressDTO defaultAddressDTO = customerDTO.getAddresses().stream()
                    .filter(address -> address.getAddressId() == customerDTO.getDefaultAddress())
                    .findFirst()
                    .orElse(null);
            model.addAttribute("cartItems", cartDTO.getCartDtoItems());
            model.addAttribute("address", defaultAddressDTO);
            model.addAttribute("customerId", userId);

            logger.info("Cart details retrieved successfully for user ID: " + userId);
        } else {
            logger.warn("No cart found for user ID: " + userId);
        }
        return "cart";
    }
}

