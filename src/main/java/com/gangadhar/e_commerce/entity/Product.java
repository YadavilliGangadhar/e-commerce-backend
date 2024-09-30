package com.gangadhar.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private Double price;
    @Column
    private String description;
    @Column
    private Integer stock;
    @Column
    private String imagePath;  // Path to the image file


    // Getters and Setters
}
