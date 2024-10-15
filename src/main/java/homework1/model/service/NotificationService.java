package homework1.model.service;

import homework1.model.entity.User;

/**
 * Интерфейс для отправки уведомлений пользователю.
 */
public interface NotificationService {

    /**
     * Отправляет email-уведомление пользователю.
     *
     * @param user Пользователь, которому отправляется уведомление.
     * @param message Сообщение, которое нужно отправить.
     */
    void sendNotification(User user, String message);
}