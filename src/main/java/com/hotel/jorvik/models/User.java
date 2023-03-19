package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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

    @NotNull
    @Min(value = 0, message = "Discount must be a non-negative number")
    @Max(value = 100, message = "Discount cannot be greater than 100")
    @Column(name = "discount")
    private int discount;

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokens;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<EntertainmentReservations> entertainmentReservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<RoomReservations> roomReservations;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phone, int discount, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.discount = discount;
        this.password = password;
        this.roles.add(role);
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
        return enabled;
    }
}
