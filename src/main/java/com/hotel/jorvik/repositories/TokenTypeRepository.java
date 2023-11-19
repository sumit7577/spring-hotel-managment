package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.TokenType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for TokenType entities. Extends JpaRepository to manage database operations
 * for token types. Includes a method to find a TokenType by its specific enumerated type.
 */
@Repository
public interface TokenTypeRepository extends JpaRepository<TokenType, Integer> {
  Optional<TokenType> findByType(TokenType.TokenTypeEnum type);
}
