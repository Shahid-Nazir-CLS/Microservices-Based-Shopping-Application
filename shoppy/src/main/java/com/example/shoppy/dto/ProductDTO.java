package com.example.shoppy.dto;

import java.util.Objects;

public class ProductDTO {
    private String title;
    private String description;
    private String imageUrl;
    private int quantity;

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Constructors, getters, and setters (omitted for brevity)

    public ProductDTO(String title, String description, String imageUrl, int quantity) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public ProductDTO() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductDTO title(String title) {
        setTitle(title);
        return this;
    }

    public ProductDTO description(String description) {
        setDescription(description);
        return this;
    }

    public ProductDTO imageUrl(String imageUrl) {
        setImageUrl(imageUrl);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductDTO)) {
            return false;
        }
        ProductDTO product = (ProductDTO) o;
        return Objects.equals(title, product.title) && Objects.equals(description, product.description)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, imageUrl);
    }

    @Override
    public String toString() {
        return "{" +
                " title='" + getTitle() + "'" +
                ", description='" + getDescription() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                "}";
    }

}
