package com.gangadhar.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserUtilityService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserUtilityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Encodes the given plain text password.
     *
     * @param plainPassword the plain text password
     * @return the encoded password
     */
    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Validates if the username meets the criteria (e.g., non-empty, alphanumeric).
     *
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    public boolean isUsernameValid(String username) {
        // Example criteria: must be alphanumeric and 3-15 characters long
        String usernamePattern = "^[a-zA-Z0-9]{3,15}$";
        return Pattern.matches(usernamePattern, username);
    }

    /**
     * Validates if the password meets the criteria (e.g., at least 8 characters, one number).
     *
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    public boolean isPasswordValid(String password) {
        // Example criteria: at least 8 characters, at least one number
        String passwordPattern = "^(?=.*[0-9]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    /**
     * Hashes the password and returns it.
     *
     * @param password the plain text password
     * @return the hashed password
     */
    public String hashPassword(String password) {
        return encodePassword(password);
    }
}
