package com.example.shoppy.dto;

import java.util.Objects;

public class ItemDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private int rating;
    private String imageURL;
    private String category;
    private int quantity;
    private int remainingQuantity;
    

    public ItemDTO() {
    }

    public ItemDTO(int id, String name, String description, int price, int rating, String imageURL, String category,
            int quantity, int remainingQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageURL = imageURL;
        this.category = category;
        this.quantity = quantity;
        this.remainingQuantity = remainingQuantity;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRemainingQuantity() {
        return this.remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", price='" + getPrice() + "'" +
                ", rating='" + getRating() + "'" +
                ", imageURL='" + getImageURL() + "'" +
                ", category='" + getCategory() + "'" +
                ", quantity='" + getQuantity() + "'" +
                ", remainingQuantity='" + getRemainingQuantity() + "'" +
                "}";
    }

}
