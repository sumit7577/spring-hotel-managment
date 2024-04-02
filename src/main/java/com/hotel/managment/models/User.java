package com.hotel.managment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user in the system, including their personal details, roles, and authentication
 * information. This entity is mapped to the "User" table in the database. It implements the Spring
 * Security interface {@link UserDetails} for authentication and authorization purposes. User
 * details include first name, last name, email, phone number, discount, password, roles, and
 * various associations.
 */
@Data
@Entity
@Table(
    name = "User",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "email"),
      @UniqueConstraint(columnNames = "phone")
    })
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "LastName is required")
  @Size(min = 3, max = 50, message = "LastName cannot be less that 3 and more than 50 characters")
  @Column(name = "last_name")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be a valid email address")
  @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
  @Column(name = "email")
  private String email;

  @NotBlank(message = "Phone number is required")
  @Size(max = 20, message = "Phone number must be at most 20 characters long")
  @Pattern(regexp = "^\\+?[0-9\\s]*$", message = "Phone number must contain only digits and spaces")
  @Column(name = "phone")
  private String phone;

  @Min(value = 0, message = "Discount must be a non-negative number")
  @Max(value = 100, message = "Discount cannot be greater than 100")
  @Column(name = "discount")
  private int discount;

  @NotBlank(message = "Password is required")
  @Column(name = "password")
  private String password;

  @Column(name = "verified")
  Timestamp verified;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Token> tokens;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<EntertainmentReservation> entertainmentReservations;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<RoomReservation> roomReservations;

  public User() {}

  /**
   * Constructs a new User instance with the provided user details.
   *
   * @param firstName The first name of the user.
   * @param lastName The last name of the user.
   * @param email The email address of the user.
   * @param phone The phone number of the user.
   * @param discount The discount applicable to the user.
   * @param password The hashed password of the user.
   * @param role The role assigned to the user.
   */
  public User(
      String firstName,
      String lastName,
      String email,
      String phone,
      int discount,
      String password,
      Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.discount = discount;
    this.password = password;
    this.roles.add(role);
    this.verified = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
