package com.gangadhar.e_commerce.service;

import com.gangadhar.e_commerce.entity.User;
import com.gangadhar.e_commerce.entity.UserProfile;
import com.gangadhar.e_commerce.repository.UserProfileRepository;
import com.gangadhar.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository; // Inject the UserRepository

    public Optional<UserProfile> getUserProfile(Long id) {
        return userProfileRepository.findById(id);
    }

    public UserProfile createOrUpdateUserProfile(Long userId, UserProfile userProfile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userProfile.setUser(user); // Set the user in the profile
        return userProfileRepository.save(userProfile); // Save the user profile
    }

    // Other methods for creating or deleting profiles can be added here
}
