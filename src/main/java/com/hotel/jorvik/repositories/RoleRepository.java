package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
