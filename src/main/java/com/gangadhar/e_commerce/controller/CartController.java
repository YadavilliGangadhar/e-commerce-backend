package com.gangadhar.e_commerce.controller;

import com.gangadhar.e_commerce.entity.CartItem;
import com.gangadhar.e_commerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add item to cart
    @PostMapping
    public CartItem addItemToCart(@RequestBody CartItem cartItem) {
        return cartService.addItem(cartItem);
    }

    // Get all items in cart
    @GetMapping("/user/get")
    public List<CartItem> getCartItems() {
        return cartService.getAllItems();
    }

    // Remove item from cart
    @DeleteMapping("/user/{itemId}")
    public void removeItemFromCart(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
    }
}
