package homework1.service.component;

import homework1.service.NotificationService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
public class HabitReminderScheduler {
    //в будущем реализую отправку уведомлений с помощью spring scheduler
    private final ScheduledExecutorService scheduler;
    private final NotificationService notificationService;

    public void scheduleDailyReminders(){

    }
}
