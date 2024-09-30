package com.gangadhar.e_commerce.service;

import com.gangadhar.e_commerce.entity.Role;
import com.gangadhar.e_commerce.entity.User;
import com.gangadhar.e_commerce.model.RegisterRequestDTO;
import com.gangadhar.e_commerce.repository.RoleRepository;
import com.gangadhar.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final UserUtilityService userUtilityService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserUtilityService userUtilityService) {
        this.userRepository = userRepository;
        this.userUtilityService = userUtilityService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Assuming userRepository is of type UserRepository which extends JpaRepository<User, Long>
        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Map roles to GrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Ensure role names start with "ROLE_"
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    //method to get userid
    public Long getUserIdByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(User::getId).orElse(null); // Return user ID or null if not found
    }



    public void registerUser(RegisterRequestDTO registrationDto) {
        // Check if the username already exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(userUtilityService.hashPassword(registrationDto.getPassword())); // Encrypt password

        // Fetch roles from the database or create new roles
        List<Role> roles = new ArrayList<>();
        for (String roleName : registrationDto.getRoles()) {
            Optional<Role> optionalRole = roleRepository.findByName(roleName); // Assuming this returns Optional<Role>

            // Check if the role exists
            Role role = optionalRole.orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(roleName);
                return roleRepository.save(newRole); // Save the new role if it doesn't exist
            });

            roles.add(role);
        }

        user.setRoles(roles); // Set the roles
        userRepository.save(user); // Save the user
    }

    //save user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

