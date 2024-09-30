package com.gangadhar.e_commerce.controller;

import com.gangadhar.e_commerce.config.JwtUtil;
import com.gangadhar.e_commerce.model.AuthenticationRequest;
import com.gangadhar.e_commerce.model.RegisterRequestDTO;
import com.gangadhar.e_commerce.service.UserService; // Ensure your UserService is correctly imported
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth") // Define a base path for authentication
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint for user authentication
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            // Load user details and generate JWT
            UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Get roles from UserDetails (assuming UserDetails implements methods to get roles)
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Retrieve user ID (assuming you have a method to get the user ID)
            Long userId = userService.getUserIdByUsername(authenticationRequest.getUsername());


            // Prepare the response
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Login successful");
            successResponse.put("token", jwt);
            successResponse.put("username", userDetails.getUsername()); // Add username to the response
            successResponse.put("roles", roles); // Adding user roles to the response
            successResponse.put("userid", userId);


            return ResponseEntity.ok(successResponse);

        } catch (BadCredentialsException e) {
            // Prepare error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Incorrect username or password");
            errorResponse.put("timestamp", new Date());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            // Generic exception handling
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Login failed due to server error");
            errorResponse.put("details", e.getMessage());
            errorResponse.put("timestamp", new Date());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Add a registration endpoint
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            userService.registerUser(registerRequest); // Implement this method in your service
            String message = "User registered successfully";
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error registering user: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // Optional: Add a token validation endpoint
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7); // Extract the token
            String username = jwtUtil.extractUsername(jwt); // Extract username from token

            // Load user details
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Validate the token
            boolean isValid = jwtUtil.validateToken(jwt, userDetails);
            return ResponseEntity.ok(isValid);
        }
        return ResponseEntity.badRequest().body(false); // Return false for invalid cases
    }
}
