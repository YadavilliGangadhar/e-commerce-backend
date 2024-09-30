package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.CartItem;
import com.gangadhar.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}
