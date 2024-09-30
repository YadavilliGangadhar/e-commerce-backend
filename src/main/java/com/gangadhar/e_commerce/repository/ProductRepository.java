package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional custom query methods can be added here if needed
}

