package com.example.shoppy.dto;

import java.util.List;
import java.util.Objects;


public class CustomerDTO {
    private int customerId;
    private String email;
    private Long googleId;
    private String username;
    private String profilePic;
    private Integer defaultAddress;
    private List<AddressDTO> addresses;


    public CustomerDTO() {
    }

    public CustomerDTO(int customerId, String email, Long googleId, String username, String profilePic, Integer defaultAddress, List<AddressDTO> addresses) {
        this.customerId = customerId;
        this.email = email;
        this.googleId = googleId;
        this.username = username;
        this.profilePic = profilePic;
        this.defaultAddress = defaultAddress;
        this.addresses = addresses;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGoogleId() {
        return this.googleId;
    }

    public void setGoogleId(Long googleId) {
        this.googleId = googleId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getDefaultAddress() {
        return this.defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<AddressDTO> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public CustomerDTO customerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public CustomerDTO email(String email) {
        setEmail(email);
        return this;
    }

    public CustomerDTO googleId(Long googleId) {
        setGoogleId(googleId);
        return this;
    }

    public CustomerDTO username(String username) {
        setUsername(username);
        return this;
    }

    public CustomerDTO profilePic(String profilePic) {
        setProfilePic(profilePic);
        return this;
    }

    public CustomerDTO defaultAddress(Integer defaultAddress) {
        setDefaultAddress(defaultAddress);
        return this;
    }

    public CustomerDTO addresses(List<AddressDTO> addresses) {
        setAddresses(addresses);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CustomerDTO)) {
            return false;
        }
        CustomerDTO customerDTO = (CustomerDTO) o;
        return customerId == customerDTO.customerId && Objects.equals(email, customerDTO.email) && Objects.equals(googleId, customerDTO.googleId) && Objects.equals(username, customerDTO.username) && Objects.equals(profilePic, customerDTO.profilePic) && Objects.equals(defaultAddress, customerDTO.defaultAddress) && Objects.equals(addresses, customerDTO.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, googleId, username, profilePic, defaultAddress, addresses);
    }

    @Override
    public String toString() {
        return "{" +
            " customerId='" + getCustomerId() + "'" +
            ", email='" + getEmail() + "'" +
            ", googleId='" + getGoogleId() + "'" +
            ", username='" + getUsername() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", defaultAddress='" + getDefaultAddress() + "'" +
            ", addresses='" + getAddresses() + "'" +
            "}";
    }
    
}
