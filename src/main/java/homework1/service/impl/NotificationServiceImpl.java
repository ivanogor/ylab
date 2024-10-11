package homework1.service.impl;

import homework1.entity.User;
import homework1.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(User user, String message) {
        // логика отправки письма на почту.
        // в будущем сделаю реализацию с использованием стороней библиотеки javax.mail
    }
}
