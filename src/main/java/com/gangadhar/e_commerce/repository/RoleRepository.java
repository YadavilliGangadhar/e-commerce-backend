package com.gangadhar.e_commerce.repository;

import com.gangadhar.e_commerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name); // Custom query to find a role by name
}
