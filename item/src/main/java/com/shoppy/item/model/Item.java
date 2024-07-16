package com.shoppy.item.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, unique = true)
    private int Id;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_price", nullable = false)
    private int price;

    @Column(name = "item_rating")
    private int rating;

    @Column(name = "item_quantity")
    private int quantity;

    @Column(name = "remaining_quantity")
    private int remainingQuantity;

    @Column(name = "item_image_url")
    private String imageURL;

    @Column(name = "item_category")
    private String category;
}
