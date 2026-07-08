package in.ssf.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import in.ssf.event.dto.PasswordResetEvent;
import in.ssf.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordResetConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "Password-Topic",groupId = "notification-group")
    public void consume(PasswordResetEvent event) {

        notificationService.handlePasswordReset(event);

    }

}