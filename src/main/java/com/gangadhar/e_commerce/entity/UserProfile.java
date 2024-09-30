package com.gangadhar.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Profile ID

    private String email;
    private String fullName;
    private String mobileNo;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String gender;
    private String occupation;
    private String profileImageUrl;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // Assuming User has a field 'id'

}
