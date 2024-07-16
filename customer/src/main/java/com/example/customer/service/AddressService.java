package com.example.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.dto.AddressDTO;
import com.example.customer.model.Address;
import com.example.customer.model.Customer;
import com.example.customer.repository.AddressDAO;
import com.example.customer.repository.CustomerDAO;

@Service
public class AddressService {
    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    // getItem
    public AddressDTO getAddress(int addressId) {
        // convert Item returned by addressDAO to itemDTO object using modelmapper
        return modelMapper.map(addressDAO.getReferenceById(addressId), AddressDTO.class);
    }

    // getAddressesForUserId
    public List<AddressDTO> getAddressesForUser(int userId) {
        List<Address> addresses = addressDAO.findAllForUser(userId);
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    // save
    public AddressDTO saveAddress(AddressDTO addressDto) {
        // first convert dto to item and save and get item back
        Address address = addressDAO.save(modelMapper.map(addressDto, Address.class));

        // then convert item to dto and return
        return modelMapper.map(address, AddressDTO.class);
    }

    // update
    public AddressDTO updateAddress(int addressId, AddressDTO addressDto) {
        Address address = addressDAO.getReferenceById(addressId);
        System.out.println(address);

        if (address != null) {
            // first convert dto to item and save and get item back
            address.setAddress(addressDto.getAddress());
            address.setCountry(addressDto.getCountry());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setPincode(addressDto.getPincode());
            address.setPhoneNumber(addressDto.getPhoneNumber());
            address.setCustomerId(addressDto.getCustomerId()); // Update the customer ID if necessary
            address = addressDAO.save(address);

            // then convert item to dto and return
            return modelMapper.map(address, AddressDTO.class);
        }
        return null;
    }

    // getAll
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressDAO.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    // delete
    public void deleteAddress(int addressId) {
        // check if its default address, then remove from customer also
        Address address = addressDAO.getReferenceById(addressId);
        Customer customer = customerDAO.getReferenceById(address.getCustomerId());
        addressDAO.deleteById(addressId);
        if(customer.getDefaultAddress() == addressId){
            customerService.setDefaultAddress(address.getCustomerId(), null);
            System.out.println("deleted");
        }
    }
}
