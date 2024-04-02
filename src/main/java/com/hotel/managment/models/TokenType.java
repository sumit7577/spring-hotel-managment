package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a type of token used for various purposes in the application, such as access tokens,
 * email confirmation tokens, and reset password tokens. This entity is mapped to the "Token_Type"
 * table in the database. It defines the different categories or types of tokens available for use.
 */
@Data
@Entity
@Table(name = "Token_Type")
public class TokenType {

  /**
   * Enum representing different types of tokens used in the system.
   *
   * <p>This enum defines the various token types that can be associated with user actions, such as
   * access tokens, email confirmation tokens, and reset password tokens.
   */
  public enum TokenTypeEnum {
    ACCESS,
    EMAIL_CONFIRMATION,
    RESET_PASSWORD
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private TokenTypeEnum type;

  public TokenType() {}

  public TokenType(TokenTypeEnum type) {
    this.type = type;
  }
}
