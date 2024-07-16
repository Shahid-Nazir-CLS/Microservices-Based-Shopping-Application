package com.example.shoppy.dto;

import java.util.Objects;

public class AddressDTO {
    private int addressId;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String phoneNumber;
    private int customerId;


    public AddressDTO() {
    }

    public AddressDTO(int addressId, String address, String city, String state, String country, String pincode, String phoneNumber, int customerId) {
        this.addressId = addressId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.phoneNumber = phoneNumber;
        this.customerId = customerId;
    }

    public int getAddressId() {
        return this.addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public AddressDTO addressId(int addressId) {
        setAddressId(addressId);
        return this;
    }

    public AddressDTO address(String address) {
        setAddress(address);
        return this;
    }

    public AddressDTO city(String city) {
        setCity(city);
        return this;
    }

    public AddressDTO state(String state) {
        setState(state);
        return this;
    }

    public AddressDTO country(String country) {
        setCountry(country);
        return this;
    }

    public AddressDTO pincode(String pincode) {
        setPincode(pincode);
        return this;
    }

    public AddressDTO phoneNumber(String phoneNumber) {
        setPhoneNumber(phoneNumber);
        return this;
    }

    public AddressDTO customerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AddressDTO)) {
            return false;
        }
        AddressDTO addressDTO = (AddressDTO) o;
        return addressId == addressDTO.addressId && Objects.equals(address, addressDTO.address) && Objects.equals(city, addressDTO.city) && Objects.equals(state, addressDTO.state) && Objects.equals(country, addressDTO.country) && Objects.equals(pincode, addressDTO.pincode) && Objects.equals(phoneNumber, addressDTO.phoneNumber) && customerId == addressDTO.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, address, city, state, country, pincode, phoneNumber, customerId);
    }

    @Override
    public String toString() {
        return "{" +
            " addressId='" + getAddressId() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            "}";
    }
    

}
