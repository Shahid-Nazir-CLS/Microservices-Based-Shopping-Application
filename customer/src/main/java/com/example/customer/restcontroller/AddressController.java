package com.example.customer.restcontroller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.customer.dto.AddressDTO;
import com.example.customer.dto.ErrorDetails;
import com.example.customer.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "address", description = "Methods of Address Service")
public class AddressController {

    private static final Logger logger = LogManager.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Get Address by ID", description = "Retrieve an address by its ID")
    @GetMapping("/{addressId}")
    public AddressDTO getAddressById(@PathVariable("addressId") int addressId) {
        logger.info("Retrieving address with ID: {}", addressId);
        return addressService.getAddress(addressId);
    }

    @Operation(summary = "Get All Addresses", description = "Retrieve all addresses")
    @GetMapping("/")
    public List<AddressDTO> getAddresses() {
        logger.info("Retrieving all addresses");
        return addressService.getAddresses();
    }

    @Operation(summary = "Get Addresses by User ID", description = "Retrieve all addresses for a specific user by user ID")
    @GetMapping("/user/{userId}")
    public List<AddressDTO> getAddressesForUserId(@PathVariable("userId") int userId) {
        logger.info("Retrieving addresses for user ID: {}", userId);
        return addressService.getAddressesForUser(userId);
    }

    @Operation(summary = "Save Address", description = "Save a new address")
    @PostMapping("/")
    public AddressDTO saveAddress(@RequestBody AddressDTO addressDTO) {
        logger.info("Saving new address: {}", addressDTO);
        return addressService.saveAddress(addressDTO);
    }

    @Operation(summary = "Update Address", description = "Update an existing address by its ID")
    @PutMapping("/{addressId}")
    public AddressDTO updateAddress(@PathVariable("addressId") int addressId, @RequestBody AddressDTO addressDTO) {
        logger.info("Updating address with ID: {}", addressId);
        return addressService.updateAddress(addressId, addressDTO);
    }

    @Operation(summary = "Delete Address", description = "Delete an address by its ID")
    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable("addressId") int addressId) {
        logger.info("Deleting address with ID: {}", addressId);
        addressService.deleteAddress(addressId);
        return "Address deleted";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
