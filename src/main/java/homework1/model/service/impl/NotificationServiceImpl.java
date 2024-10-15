package homework1.model.service.impl;

import homework1.model.entity.User;
import homework1.model.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(User user, String message) {
        // логика отправки письма на почту.
        // в будущем сделаю реализацию с использованием стороней библиотеки javax.mail
    }
}
