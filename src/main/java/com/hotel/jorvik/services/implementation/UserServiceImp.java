package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.EmailChangeDTO;
import com.hotel.jorvik.models.DTO.PasswordChangeDTO;
import com.hotel.jorvik.models.DTO.UserDTO;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public Iterable<UserDTO> getAll() {
        Iterable<User> users = repository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(int id) {
        Optional<User> user = repository.findById(id);
        return user.map(UserDTO::new).orElse(null);
    }

    @Override
    public boolean updateById(int id, UserDTO newUser) {
        Optional<User> userToEdit = repository.findById(id);
        if (userToEdit.isEmpty()) {
            return false;
        }
        User user = userToEdit.get();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setDiscount(newUser.getDiscount());
        repository.save(user);
        return true;
    }

    @Override
    public boolean updatePassword(PasswordChangeDTO passwordChangeDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            passwordChangeDTO.getPassword()
                    )
            );
        }catch (Exception e){
            return false;
        }
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        repository.save(user.get());
        return true;
    }

    @Override
    public boolean updateEmail(EmailChangeDTO emailChangeDTO) {
        repository.findByEmail(emailChangeDTO.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setEmail(emailChangeDTO.getEmail());
        user.get().setConfirmed(false);
        emailService.sendConfirmationEmail(user.get());
        repository.save(user.get());
        return true;
    }

    @Override
    public boolean resentEmailVerification() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        emailService.sendConfirmationEmail(user.get());
        return true;
    }
}
