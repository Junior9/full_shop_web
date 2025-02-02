package com.api.shop.demo.service.notification;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.SendEmailError;
import com.api.shop.demo.model.User;
import com.api.shop.demo.service.user.Userservice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationServiceInter{

    private final Userservice userservice;

    @Override
    public Optional<Boolean> sendEmail(Long userId, String message) {
        try {
            User user = this.userservice.getById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found id " + userId));

            System.out.println("Email send to user " + user.getName() + " email " + user.getEmail() + " message : " + message);
            
            return Optional.of(Boolean.TRUE);
        } catch (Exception error) {
            throw new SendEmailError("Error to send email " + error.getMessage());
        }
    }

}
