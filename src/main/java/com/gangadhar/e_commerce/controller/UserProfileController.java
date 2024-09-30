package com.gangadhar.e_commerce.controller;

import com.gangadhar.e_commerce.entity.UserProfile;
import com.gangadhar.e_commerce.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long userId) {
        Optional<UserProfile> profile = userProfileService.getUserProfile(userId);
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserProfile> createOrUpdateUserProfile(@PathVariable Long userId, @RequestBody UserProfile userProfile) {
        UserProfile updatedProfile = userProfileService.createOrUpdateUserProfile(userId, userProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    // Additional endpoints for creating and deleting profiles can be added here
}
