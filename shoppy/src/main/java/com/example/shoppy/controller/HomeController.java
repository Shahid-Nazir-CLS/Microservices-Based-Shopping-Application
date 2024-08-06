package com.example.shoppy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shoppy.dto.CustomerDTO;
import com.example.shoppy.dto.ItemDTO;
import com.example.shoppy.dto.ItemsResponseDTO;
import com.example.shoppy.dto.UserSessionDTO;
import com.example.shoppy.service.CustomerService;
import com.example.shoppy.service.ItemService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserSessionDTO userSessionDTO;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String getHome(@RequestParam(name = "page", defaultValue = "0") String pageNumber, Model model, HttpSession session) {
        logger.info("Fetching home page items for page number: " + pageNumber);

        ItemsResponseDTO response = itemService.getItems(pageNumber);
        model.addAttribute("items", response.getEmbeddedItems().getItems());
        model.addAttribute("totalPages", response.getPage().getTotalPages());
        model.addAttribute("java -jar zipkin-server-3.3.0-exec.jarcurrPageNumber", response.getPage().getNumber());
        model.addAttribute("linksProvided", true);
        model.addAttribute("isLoggedIn", false);
        model.addAttribute("username", userSessionDTO.getUsername());

        logger.info("Home page items retrieved successfully for page number: " + pageNumber);
        
        // get email from security context
        String email = null;

        // Retrieve Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve principal (authenticated user)
            Object principal = authentication.getPrincipal();
            
            // Check if principal is an instance of UserDetails (which typically represents your custom user details)
            if (principal instanceof DefaultOAuth2User) {
                DefaultOAuth2User oauth2User = (DefaultOAuth2User) principal;
                email = oauth2User.getAttribute("email");
            }
            if(email != null){
                // get logged in customer from email
                CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
                System.out.println(customerDTO);
                // set global session variables
                userSessionDTO.setLoggedIn(true);
                userSessionDTO.setUserId(customerDTO.getCustomerId());
                userSessionDTO.setUsername(customerDTO.getUsername());

                model.addAttribute("isLoggedIn", true);
                model.addAttribute("username", userSessionDTO.getUsername());
            }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
            }
        }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
            }
        return "home";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam("keyword") String keyword, Model model) {
        logger.info("Searching items with keyword: " + keyword);

        List<ItemDTO> items = itemService.searchItems(keyword);
        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("linksProvided", false);
        model.addAttribute("isLoggedIn", userSessionDTO.isLoggedIn());
        model.addAttribute("username", userSessionDTO.getUsername());

        // get email from security context
        String email = null;

        // Retrieve Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve principal (authenticated user)
            Object principal = authentication.getPrincipal();
            
            // Check if principal is an instance of UserDetails (which typically represents your custom user details)
            if (principal instanceof DefaultOAuth2User) {
                DefaultOAuth2User oauth2User = (DefaultOAuth2User) principal;
                email = oauth2User.getAttribute("email");
            }
            System.out.println(email);
            if(email != null){
                // get logged in customer from email
                CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
                System.out.println(customerDTO);
                // set global session variables
                userSessionDTO.setLoggedIn(true);
                userSessionDTO.setUserId(customerDTO.getCustomerId());
                userSessionDTO.setUsername(customerDTO.getUsername());

                model.addAttribute("isLoggedIn", true);
                model.addAttribute("username", userSessionDTO.getUsername());
            }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
            }
        }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
        }


        logger.info("Items searched successfully for keyword: " + keyword);
        return "home";
    }

    @GetMapping("/itemDetails/{itemId}")
    public String getItemDetails(@PathVariable("itemId") String itemId, Model model) {
        logger.info("Fetching details for item ID: " + itemId);

        ItemDTO itemDto = itemService.getItemDetails(itemId);
        model.addAttribute("item", itemDto);
        model.addAttribute("isLoggedIn", userSessionDTO.isLoggedIn());
        model.addAttribute("username", userSessionDTO.getUsername());

        // get email from security context
        String email = null;

        // Retrieve Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve principal (authenticated user)
            Object principal = authentication.getPrincipal();
            
            // Check if principal is an instance of UserDetails (which typically represents your custom user details)
            if (principal instanceof DefaultOAuth2User) {
                DefaultOAuth2User oauth2User = (DefaultOAuth2User) principal;
                email = oauth2User.getAttribute("email");
            }
            System.out.println(email);
            if(email != null){
                // get logged in customer from email
                CustomerDTO customerDTO = customerService.getCustomerByEmail(email);
                System.out.println(customerDTO);
                // set global session variables
                userSessionDTO.setLoggedIn(true);
                userSessionDTO.setUserId(customerDTO.getCustomerId());
                userSessionDTO.setUsername(customerDTO.getUsername());

                model.addAttribute("isLoggedIn", true);
                model.addAttribute("username", userSessionDTO.getUsername());
            }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
            }
        }else{
                userSessionDTO.setLoggedIn(false);
                userSessionDTO.setUserId(0);
                userSessionDTO.setUsername(null);
        }

        logger.info("Item details retrieved successfully for item ID: " + itemId);
        return "itemDetails";
    }
}
