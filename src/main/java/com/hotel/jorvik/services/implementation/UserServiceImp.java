package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.user.*;
import com.hotel.jorvik.models.TokenType;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.models.DTO.auth.AuthenticationResponse;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final SecurityTools securityTools;
    private final JwtService jwtService;

    @Override
    public List<UserDTO> getAll() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.map(UserDTO::new).get();
    }

    @Override
    public UserDTO getByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.map(UserDTO::new).get();
    }

    @Override
    public boolean checkEmailVerification() {
        User user = securityTools.retrieveUserData();
        return user.getVerified() != null;
    }

    @Override
    @Transactional
    public void updatePassword(PasswordChangeRequest passwordChangeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!securityTools.isValidPassword(passwordChangeRequest.getNewPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        passwordChangeRequest.getPassword()
                )
        );
        User user = securityTools.retrieveUserData();
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public AuthenticationResponse updateEmail(EmailChangeRequest emailChangeRequest) {
        userRepository.findByEmail(emailChangeRequest.getEmail())
                .ifPresent(row -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        User user = securityTools.retrieveUserData();

        user.setEmail(emailChangeRequest.getEmail());
        user.setVerified(null);
        emailService.sendConfirmationEmail(user);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        jwtService.revokeAllUserTokens(user, TokenType.ETokenType.ACCESS);
        jwtService.saveUserToken(user, jwtToken, TokenType.ETokenType.ACCESS);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public void resentEmailVerification() {
        User user = securityTools.retrieveUserData();
        emailService.sendConfirmationEmail(user);
    }

    @Override
    @Transactional
    public void updatePhone(PhoneChangeRequest phoneChangeRequest) {
        if (!securityTools.isValidPhone(phoneChangeRequest.getPhone())) {
            throw new IllegalArgumentException("Phone is not valid");
        }
        User user = securityTools.retrieveUserData();
        user.setPhone(phoneChangeRequest.getPhone());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateDiscount(int id, DiscountChangeRequest discountChangeRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setDiscount(discountChangeRequest.getDiscount());
        userRepository.save(user.get());
    }

    @Override
    public int getUserRoomReservationsCount() {
        User user = securityTools.retrieveUserData();
        return user.getRoomReservations().size();
    }

    @Override
    public int getUserEntertainmentReservationsCount() {
        User user = securityTools.retrieveUserData();
        return user.getEntertainmentReservations().size();
    }
}
