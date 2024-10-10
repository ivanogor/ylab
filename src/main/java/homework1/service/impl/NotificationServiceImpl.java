package homework1.service.impl;

import homework1.entity.User;
import homework1.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendEmailNotification(User user, String message) {
        // логика отправки письма на почту.
        // в будущем сделаю реализацию с использованием стороней библиотеки javax.mail
    }

    @Override
    public void sendPushNotification(User user, String message) {
        // логика отправки письма на почту.
        // вижу реализацию с использованием стороней библиотеки com.google.firebase
    }
}
