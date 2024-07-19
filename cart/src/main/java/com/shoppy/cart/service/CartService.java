package com.shoppy.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shoppy.cart.dto.CartDTO;
import com.shoppy.cart.dto.CartItemDTO;
import com.shoppy.cart.dto.ItemDTO;
import com.shoppy.cart.dto.ItemDTOsResponse;
import com.shoppy.cart.model.Cart;
import com.shoppy.cart.model.CartItem;
import com.shoppy.cart.repository.CartDAO;
import com.shoppy.cart.repository.CartItemDAO;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private CartItemDAO cartItemDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    // getCart
    public CartDTO getCart(int userId) {

        // check if a user has a cart
        Cart cart = cartDAO.findByUserId(userId);

        // if user has a cart
        if (cart != null) {
            System.out.println("hello");

            cart = cartDAO.getReferenceById(cart.getCartId());

            // query item table for details
            // get all itemIds so we can get Items of the order so only one call to items
            List<Integer> itemIds = cart.getCartItems().stream().map(CartItem::getItemId).collect(Collectors.toList());

            // get items from ids
            ItemDTOsResponse itemDTOsResponse = restTemplate.postForObject("http://item-service/api/v1/items/byIds",
                    itemIds,
                    ItemDTOsResponse.class);

            // create map of items
            Map<Integer, ItemDTO> itemDTOMap = itemDTOsResponse.getItemDTOs().stream()
                    .collect(Collectors.toMap(ItemDTO::getId, itemDTO -> itemDTO));

            // then convert cart to cartdto manually and return
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCustomerId(cart.getCustomerId());
            cartDTO.setCartId(cart.getCartId());

            List<CartItemDTO> dtos = new ArrayList<CartItemDTO>();
            for (CartItem ci : cart.getCartItems()) {
                CartItemDTO cartItemDTO = modelMapper.map(ci, CartItemDTO.class);
                cartItemDTO.setItemDTO(itemDTOMap.get(ci.getItemId()));
                dtos.add(cartItemDTO);
            }
            cartDTO.setCartDtoItems(dtos);
            return cartDTO;
        }
        return null;
    }

    // save
    public CartItemDTO saveItem(int userId, int itemId) {

        // check if a user has a cart
        Cart cart = cartDAO.findByUserId(userId);

        // check if cart item already in cart then update quantity and don't add new item
        boolean itemExists = false;
        CartItemDTO res = null;
        CartItemDTO cartItemDTO;


        if(cart != null){
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.getItemId() == itemId) {
                    cartItemDTO = modelMapper.map(cartItemDAO.getReferenceById(cartItem.getCartItemId()), CartItemDTO.class);
                    cartItemDTO.setQuantity(cartItem.getQuantity() + 1);
                    res = updateItemQuantity(userId, cartItem.getCartItemId(), cartItemDTO.getQuantity());
                    itemExists = true;
                    break;
                }
            }
        }
        if (!itemExists) {
            // if user doesn't have a cart, create a cart for him
            if (cart == null) {
                CartDTO cartDTO = new CartDTO();
                cartDTO.setCustomerId(userId);
                cart = cartDAO.save(modelMapper.map(cartDTO, Cart.class));
            }

            // else add item
            // first convert dto to item and save
            ItemDTO itemDto = restTemplate.getForObject("http://item-service/api/v1/items/"+ itemId, ItemDTO.class);
            cartItemDTO = new CartItemDTO(0, cart.getCartId(), 1, itemDto.getPrice(), itemId, itemDto); 
            CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
            cartItem.setCartId(cart.getCartId());
            cartItem.setCartItemId(0);
            cartItem = cartItemDAO.save(cartItem);

            res = modelMapper.map(cartItem, CartItemDTO.class);
        }

        return res;
    }

    // delete item
    public void removeItem(int userId, int itemId) {
        // check if a user has a cart
        Cart cart = cartDAO.findByUserId(userId);

        // if not create a cart for him
        if (cart != null) {
            // Remove the item from the cart's list of items

            CartItem cartItem = cartItemDAO.getReferenceById(itemId);

            if (cartItem != null) {
                cart.getCartItems().remove(cartItem);

                // Delete the item from the database
                cartItemDAO.deleteById(itemId);
            }

        } else {
            System.out.println("Item not found in the cart");
        }
    }

    // clear cart
    public void deleteCart(int userId) {
        // check if a user has a cart
        Cart cart = cartDAO.findByUserId(userId);

        // if not create a cart for him
        if (cart != null) {
            cartDAO.deleteById(cart.getCartId());
        }
    }

    // save
    @Transactional
    public CartItemDTO updateItemQuantity(int userId, int cartItemId, int newQuantity) {

        // check if a user has a cart
        Cart cart = cartDAO.findByUserId(userId);

        // if newQuantity is zero, remove cartItemFromCart
        if(newQuantity == 0){
            removeItem(userId, cartItemId);
            return null;
        }
        
        
        if (cart != null) {

            CartItem cartItem = cartItemDAO.getReferenceById(cartItemId);

            if (cartItem != null) {
                cartItem.setQuantity(newQuantity);
                cartItem = cartItemDAO.save(cartItem);
                return modelMapper.map(cartItem, CartItemDTO.class);
            }
        }
        return null;
    }
}
