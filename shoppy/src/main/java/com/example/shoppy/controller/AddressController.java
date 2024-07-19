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

import com.example.shoppy.dto.AddressDTO;
import com.example.shoppy.dto.UserSessionDTO;
import com.example.shoppy.service.CustomerService;

@Controller
public class AddressController {
    private static final Logger logger = LogManager.getLogger(AddressController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserSessionDTO userSessionDTO;

    @GetMapping("/newaddress/{customerId}")
    public String newAddress(Model model, @PathVariable("customerId") int customerId) {
        logger.info("Creating new address form for customer ID: " + customerId);

        model.addAttribute("customerId", userSessionDTO.getUserId());
        model.addAttribute("isLoggedIn", userSessionDTO.isLoggedIn());
        model.addAttribute("username", userSessionDTO.getUsername());
        return "newAddress";
    }

    @GetMapping("/editaddress/{addressId}")
    public String editAddress(@PathVariable("addressId") int addressId, Model model) {
        logger.info("Editing address with ID: " + addressId);

        AddressDTO addressDTO = customerService.getAddress(addressId);
        if (addressDTO == null) {
            logger.warn("Address not found for ID: " + addressId);
            // Handle address not found scenario
            return "redirect:/profile";
        }

        model.addAttribute("address", addressDTO);
        model.addAttribute("isLoggedIn", userSessionDTO.isLoggedIn());
        model.addAttribute("username", userSessionDTO.getUsername());
        return "editAddress";
    }

    @PostMapping("/saveAddress/{customerId}")
    public String saveAddress(@ModelAttribute("address") AddressDTO addressDTO, @PathVariable("customerId") int customerId) {
        logger.info("Saving address for customer ID: " + customerId);

        customerService.updateAddress(addressDTO);
        return "redirect:/profile";
    }

    @GetMapping("/address/delete/{addressId}")
    public String deleteAddress(@PathVariable("addressId") int addressId) {
        logger.info("Deleting address with ID: " + addressId);

        customerService.deleteAddress(addressId);
        return "redirect:/profile";
    }

    @GetMapping("/address/setDefault/{addressId}")
    public String setDefaultAddress(@PathVariable("addressId") int addressId) {
        logger.info("Setting address with ID: " + addressId + " as default");

        int userId = userSessionDTO.getUserId(); // Example, replace with actual user identification logic
        customerService.setDefaultAddress(userId, addressId);
        return "redirect:/profile";
    }
}

