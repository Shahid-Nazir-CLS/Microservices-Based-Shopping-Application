package com.example.customer.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import
org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.customer.dto.AddressDTO;
import com.example.customer.dto.CustomerDTO;
import com.example.customer.repository.CustomerDAO;
import com.example.customer.service.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        // check if customer exists in db
        // if not save in db
        CustomerDTO customerDTO = customerService.getCustomerByEmail(String.valueOf(attributes.get("email")));
        if (customerDTO == null) {
            customerDTO = new CustomerDTO();
            customerDTO.setEmail(String.valueOf(attributes.get("email")));
            Long googleId = Long.valueOf((String.valueOf(attributes.get("sub")).substring(0, 18)));
            customerDTO.setGoogleId(googleId);
            customerDTO.setUsername(String.valueOf(attributes.get("name")));
            customerDTO.setProfilePic(String.valueOf(attributes.get("picture")));
            customerDTO = customerService.updateCustomer(customerDTO);

        }

        // Redirect to home page or any other URL
        response.sendRedirect("http://localhost:8080");
    }
}
