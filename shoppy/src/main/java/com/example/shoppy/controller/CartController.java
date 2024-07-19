package com.example.shoppy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shoppy.dto.AddressDTO;
import com.example.shoppy.dto.CartDTO;
import com.example.shoppy.dto.CustomerDTO;
import com.example.shoppy.dto.UserSessionDTO;
import com.example.shoppy.service.CartService;
import com.example.shoppy.service.CustomerService;

@Controller
public class CartController {
    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserSessionDTO userSessionDTO;

    @GetMapping("/cart")
    public String getCart(Model model) {
        int userId = userSessionDTO.getUserId(); // Example, replace with actual user identification logic
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
            model.addAttribute("customerId", userSessionDTO.getUserId());

            logger.info("Cart details retrieved successfully for user ID: " + userId);
        } else {
            logger.warn("No cart found for user ID: " + userId);
        }
        model.addAttribute("isLoggedIn", userSessionDTO.isLoggedIn());
        model.addAttribute("username", userSessionDTO.getUsername());
        return "cart";
    }

    @GetMapping("/addItem/{itemId}/{returnPage}")
    public String addItemToCart(@PathVariable("itemId") int itemId, @PathVariable("returnPage") String returnPage) {
        int userId = userSessionDTO.getUserId();
        cartService.addItemToCart(userId, itemId);
        if (returnPage.equals("home")) {
            return "redirect:/";
        } else {
            return "redirect:/itemDetails/" + itemId;
        }
    }

    @GetMapping("/removeItem/{cartItemId}")
    public String removeItemFromCart(@PathVariable("cartItemId") int cartItemId) {
        int userId = userSessionDTO.getUserId();
        cartService.removeItemFromCart(userId, cartItemId);
        return "redirect:/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart() {
        int userId = userSessionDTO.getUserId();
        cartService.clearCart(userId);
        return "redirect:/cart";
    }

    @GetMapping("/updateQuantity/{cartItemId}")
    public String updateQuantity(@PathVariable("cartItemId") int cartItemId, @RequestParam("quantity") int newQuantity) {
        int userId = userSessionDTO.getUserId();
        cartService.updateQuantity(userId, cartItemId, newQuantity);
        return "redirect:/cart";
    }
}

