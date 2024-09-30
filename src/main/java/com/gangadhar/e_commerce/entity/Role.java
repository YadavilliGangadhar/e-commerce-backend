package com.gangadhar.e_commerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //e.g "ROLE_USER", "ROLE_ADMIN"

    // Default constructor
    public Role() {
    }

    // Constructor with name parameter
    public Role(String name) {
        this.name = name;
    }
}
