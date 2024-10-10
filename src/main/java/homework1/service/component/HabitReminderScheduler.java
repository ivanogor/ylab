package homework1.service.component;

import homework1.service.NotificationService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
public class HabitReminderScheduler {
    private final ScheduledExecutorService scheduler;
    private final NotificationService notificationService;

    public void scheduleDailyReminders(){

    }
}
