package com.example.customer.dto;

import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int customerId;
    private String email;
    private Long googleId;
    private String username;
    private String profilePic;
    private Integer defaultAddress;
    private List<AddressDTO> addresses;

    // Getters and setters (omitted for brevity)
}
