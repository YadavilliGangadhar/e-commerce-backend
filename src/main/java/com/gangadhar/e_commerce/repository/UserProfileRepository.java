package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // You can define custom query methods here if needed
}
