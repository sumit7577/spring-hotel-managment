package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a token associated with user authentication or authorization. This entity is mapped to
 * the "Token" table in the database. Tokens are used for various purposes, including user sessions,
 * authentication, and authorization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "token", nullable = false)
  private String token;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "token_type_id", referencedColumnName = "id")
  private TokenType tokenType;
}
