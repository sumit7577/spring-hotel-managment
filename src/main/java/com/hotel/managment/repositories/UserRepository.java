package com.hotel.managment.repositories;

import com.hotel.managment.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entities. Extends JpaRepository to manage database operations for
 * users. Includes methods to find users by email, phone, and combinations of first and last names.
 * Supports pagination for queries based on name.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  Optional<User> findByPhone(String phone);

  List<User> findByFirstNameContainingAndLastNameContaining(
      String firstName, String lastName, Pageable pageable);

  List<User> findByFirstNameContainingOrLastNameContaining(
      String firstName, String lastName, Pageable pageable);
}
