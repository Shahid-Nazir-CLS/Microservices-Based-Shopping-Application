package com.example.shoppy.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

public class OrderDTO {

    private int orderId;
    private int customerId;
    private Timestamp orderDate;
    private String status;
    private int totalPrice;
    private int addressId;
    private AddressDTO addressDTO;
    private List<OrderItemDTO> orderItems;



    public OrderDTO() {
    }

    public OrderDTO(int orderId, int customerId, Timestamp orderDate, String status, int totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public int getAddressId() {
        return this.addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public AddressDTO getAddressDTO() {
        return this.addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }


    public List<OrderItemDTO> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDTO orderId(int orderId) {
        setOrderId(orderId);
        return this;
    }

    public OrderDTO customerId(int customerId) {
        setCustomerId(customerId);
        return this;
    }

    public OrderDTO orderDate(Timestamp orderDate) {
        setOrderDate(orderDate);
        return this;
    }

    public OrderDTO status(String status) {
        setStatus(status);
        return this;
    }

    public OrderDTO totalPrice(int totalPrice) {
        setTotalPrice(totalPrice);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderDTO)) {
            return false;
        }
        OrderDTO order = (OrderDTO) o;
        return orderId == order.orderId && customerId == order.customerId && Objects.equals(orderDate, order.orderDate)
                && Objects.equals(status, order.status) && totalPrice == order.totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, orderDate, status, totalPrice);
    }

    @Override
    public String toString() {
        return "{" +
                " orderId='" + getOrderId() + "'" +
                ", customerId='" + getCustomerId() + "'" +
                ", orderDate='" + getOrderDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", totalPrice='" + getTotalPrice() + "'" +
                "}";
    }

}
