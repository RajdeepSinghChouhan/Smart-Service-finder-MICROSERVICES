package in.ssf.notification.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import in.ssf.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedRate = 300000)
    public void retryFailedNotifications() {

        notificationService.retryFailedNotifications();

    }

}