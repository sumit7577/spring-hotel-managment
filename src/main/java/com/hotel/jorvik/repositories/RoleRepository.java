package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Role entities. Extends JpaRepository to facilitate database operations
 * for user roles. Includes a method to find a Role by its name.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(Role.RoleEnum name);
}
