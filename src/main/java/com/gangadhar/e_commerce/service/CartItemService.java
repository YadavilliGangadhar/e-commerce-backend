package com.gangadhar.e_commerce.service;

import com.gangadhar.e_commerce.entity.CartItem;
import com.gangadhar.e_commerce.entity.User;
import com.gangadhar.e_commerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Add a new item to the cart
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Update an existing cart item (e.g., change quantity)
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Remove a specific item from the cart by ID
    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    // Retrieve all cart items for a specific user (can also be in CartService)
    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user);
    }
}

