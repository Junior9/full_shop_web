package com.api.shop.demo.service.notification;

import java.util.Optional;

public interface NotificationServiceInter {

    public Optional<Boolean> sendEmail(Long userId, String message);

}
