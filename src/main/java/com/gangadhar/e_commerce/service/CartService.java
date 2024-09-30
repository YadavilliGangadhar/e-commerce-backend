package com.gangadhar.e_commerce.service;

import com.gangadhar.e_commerce.entity.CartItem;
import com.gangadhar.e_commerce.entity.User;
import com.gangadhar.e_commerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Retrieve all cart items for a specific user
    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    // Add item to cart
    public CartItem addItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Get all items in cart
    public List<CartItem> getAllItems() {
        return cartItemRepository.findAll();
    }

    // Remove item from cart
    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    // Calculate total price of items in the cart for a specific user
    public double calculateCartTotal(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice()) // Assuming the product price is accessible
                .sum();
    }

    // Clear all items from the user's cart
    public void clearCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
}

