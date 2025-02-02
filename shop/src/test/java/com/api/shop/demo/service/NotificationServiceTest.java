package com.api.shop.demo.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.model.User;
import com.api.shop.demo.service.notification.NotificationService;
import com.api.shop.demo.service.user.Userservice;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private Userservice userservice;

    @Test
    public void sendEmail(){
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("user@test.com");
        when(this.userservice.getById(userId)).thenReturn(Optional.of(user));

        Optional<Boolean> response = this.notificationService.sendEmail(userId, "Pay");
        Assertions.assertTrue(response.get());
    }

    @Test
    public void errorSendEmailUserNotFound(){
       try {
            Long userId = 1L;
            when(this.userservice.getById(userId)).thenReturn(Optional.empty());
            Optional<Boolean> response = this.notificationService.sendEmail(userId, "Pay");
            Assertions.assertFalse(response.get());
       } catch (Exception error) {
            Assertions.assertEquals("Error to send email User not found id 1", error.getMessage());
       }
    }


}
