package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // You can add custom queries here if needed
}

