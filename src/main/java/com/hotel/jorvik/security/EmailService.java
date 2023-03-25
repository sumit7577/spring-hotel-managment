package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;

public interface EmailService {
    void sendConfirmationEmail(User user);
    void confirmEmail(String token);
    void sendResetPasswordEmail(User user);
}
