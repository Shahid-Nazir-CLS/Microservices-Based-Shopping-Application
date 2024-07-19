package com.example.customer.restcontroller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.customer.dto.AddressDTO;
import com.example.customer.dto.CustomerDTO;
import com.example.customer.dto.ErrorDetails;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerDAO;
import com.example.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "customer", description = "Methods of Customer Service")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerDAO customerDAO;

    @Operation(summary = "Home", description = "Returns a welcome message")
    @GetMapping("/home")
    public String getHome() {
        logger.info("Accessing home endpoint");
        return "welcome to home";
    }

    @Operation(summary = "Get Customer", description = "Retrieve a customer by their ID")
    @GetMapping("/{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") int customerId) {
        logger.info("Retrieving customer with ID: {}", customerId);
        return customerService.getCustomer(customerId);
    }

    @Operation(summary = "Get Customer", description = "Retrieve a customer by their Email")
    @GetMapping("/email/{email}")
    public CustomerDTO getCustomerByEmail(@PathVariable("email") String email) {
        logger.info("Retrieving customer with Email: {}", email);
        return customerService.getCustomerByEmail(email);
    }

    @Operation(summary = "Save or Update Customer", description = "Save a new customer or update an existing one")
    @PostMapping("/")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.info("Saving or updating customer: {}", customerDTO);
        return customerService.updateCustomer(customerDTO);
    }

    @Operation(summary = "Get Customer (detailed)", description = "Retrieve a customer by their ID with detailed information including addresses")
    @GetMapping("/cust/{customerId}")
    public CustomerDTO getCustomer1(@PathVariable("customerId") int customerId) {
        logger.info("Retrieving detailed customer information with ID: {}", customerId);
        Customer customer = customerDAO.getReferenceById(customerId);
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        List<AddressDTO> addressDTOs = customer.getAddresses().stream()
                                .map(address -> modelMapper.map(address, AddressDTO.class))
                                .collect(Collectors.toList());

        customerDTO.setAddresses(addressDTOs);
        return customerDTO;
    }

    @Operation(summary = "Set Default Address", description = "Set the default address for a customer")
    @PutMapping("/{customerId}/address/{addressId}")
    public CustomerDTO setDefaultAddressForUserId(@PathVariable("customerId") int customerId, @PathVariable("addressId") int addressId) {
        logger.info("Setting default address ID: {} for customer ID: {}", addressId, customerId);
        return customerService.setDefaultAddress(customerId, addressId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
