package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Ensure this method is defined

    // Method to check if a username already exists
    boolean existsByUsername(String username);
}