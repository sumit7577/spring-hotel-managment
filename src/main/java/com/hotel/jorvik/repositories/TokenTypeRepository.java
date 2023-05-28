package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenTypeRepository extends JpaRepository<TokenType, Integer> {
    Optional<TokenType> findByType(TokenType.ETokenType type);
}