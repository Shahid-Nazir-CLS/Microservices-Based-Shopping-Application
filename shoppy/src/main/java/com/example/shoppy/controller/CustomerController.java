package com.example.shoppy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.shoppy.dto.CustomerDTO;
import com.example.shoppy.service.CustomerService;


@Controller
public class CustomerController {
    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        int userId = 1; // Example, replace with actual user identification logic
        logger.info("Fetching profile details for user ID: " + userId);

        CustomerDTO customerDTO = customerService.getCustomer(userId);
        model.addAttribute("customer", customerDTO);
        model.addAttribute("addresses", customerDTO.getAddresses());
        model.addAttribute("defaultAddress", customerDTO.getDefaultAddress());

        logger.info("Profile details retrieved successfully for user ID: " + userId);
        return "profile";
    }

    @GetMapping("/editProfile/{customerId}")
    public String getEditProfile(Model model, @PathVariable("customerId") int customerId) {
        logger.info("Fetching edit profile details for customer ID: " + customerId);

        CustomerDTO customerDTO = customerService.getCustomer(customerId);
        model.addAttribute("customer", customerDTO);

        logger.info("Edit profile details retrieved successfully for customer ID: " + customerId);
        return "editProfile";
    }

    @PostMapping("/updateProfile/{customerId}")
    public String updateProfile(@ModelAttribute("customer") CustomerDTO customerDTO) {
        logger.info("Updating profile for customer ID: " + customerDTO.getCustomerId());

        customerService.updateCustomer(customerDTO);

        logger.info("Profile updated successfully for customer ID: " + customerDTO.getCustomerId());
        return "redirect:/profile";
    }
}
