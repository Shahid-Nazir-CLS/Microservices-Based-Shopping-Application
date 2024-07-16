package com.shoppy.cart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    @Column(name = "cart_id")
    private int cartId;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;


    // public CartItem() {
    // }

    // public CartItem(Integer cartItemId, Cart cart, int itemId, int quantity, int price) {
    //     this.cartItemId = cartItemId;
    //     this.cart = cart;
    //     this.itemId = itemId;
    //     this.quantity = quantity;
    //     this.price = price;
    // }

    // public Integer getCartItemId() {
    //     return this.cartItemId;
    // }

    // public void setCartItemId(Integer cartItemId) {
    //     this.cartItemId = cartItemId;
    // }

    // public Cart getCart() {
    //     return this.cart;
    // }

    // public void setCart(Cart cart) {
    //     this.cart = cart;
    // }

    // public int getItemId() {
    //     return this.itemId;
    // }

    // public void setItemId(int itemId) {
    //     this.itemId = itemId;
    // }

    // public int getQuantity() {
    //     return this.quantity;
    // }

    // public void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }

    // public int getPrice() {
    //     return this.price;
    // }

    // public void setPrice(int price) {
    //     this.price = price;
    // }

    // public CartItem cartItemId(Integer cartItemId) {
    //     setCartItemId(cartItemId);
    //     return this;
    // }

    // public CartItem cart(Cart cart) {
    //     setCart(cart);
    //     return this;
    // }

    // public CartItem itemId(int itemId) {
    //     setItemId(itemId);
    //     return this;
    // }

    // public CartItem quantity(int quantity) {
    //     setQuantity(quantity);
    //     return this;
    // }

    // public CartItem price(int price) {
    //     setPrice(price);
    //     return this;
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof CartItem)) {
    //         return false;
    //     }
    //     CartItem cartItem = (CartItem) o;
    //     return Objects.equals(cartItemId, cartItem.cartItemId) && Objects.equals(cart, cartItem.cart) && itemId == cartItem.itemId && quantity == cartItem.quantity && price == cartItem.price;
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(cartItemId, cart, itemId, quantity, price);
    // }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " cartItemId='" + getCartItemId() + "'" +
    //         ", cart='" + getCart() + "'" +
    //         ", itemId='" + getItemId() + "'" +
    //         ", quantity='" + getQuantity() + "'" +
    //         ", price='" + getPrice() + "'" +
    //         "}";
    // }
    
}
