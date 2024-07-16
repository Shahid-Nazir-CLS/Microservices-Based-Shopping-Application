package com.example.shoppy.dto;

import java.util.Objects;

public class OrderItemDTO {
    private int orderItemid;
    private int itemId;
    private int quantity;
    private int price;
    private ItemDTO itemDTO;



    public OrderItemDTO() {
    }

    public OrderItemDTO(int orderItemid, int itemId, int quantity, int price, ItemDTO itemDTO) {
        this.orderItemid = orderItemid;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.itemDTO = itemDTO;
    }

    public int getOrderItemid() {
        return this.orderItemid;
    }

    public void setOrderItemid(int orderItemid) {
        this.orderItemid = orderItemid;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemDTO getItemDTO() {
        return this.itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public OrderItemDTO orderItemid(int orderItemid) {
        setOrderItemid(orderItemid);
        return this;
    }

    public OrderItemDTO itemId(int itemId) {
        setItemId(itemId);
        return this;
    }

    public OrderItemDTO quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public OrderItemDTO price(int price) {
        setPrice(price);
        return this;
    }

    public OrderItemDTO itemDTO(ItemDTO itemDTO) {
        setItemDTO(itemDTO);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }
        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        return orderItemid == orderItemDTO.orderItemid && itemId == orderItemDTO.itemId && quantity == orderItemDTO.quantity && price == orderItemDTO.price && Objects.equals(itemDTO, orderItemDTO.itemDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemid, itemId, quantity, price, itemDTO);
    }

    @Override
    public String toString() {
        return "{" +
            " orderItemid='" + getOrderItemid() + "'" +
            ", itemId='" + getItemId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", price='" + getPrice() + "'" +
            ", itemDTO='" + getItemDTO() + "'" +
            "}";
    }
    

}
