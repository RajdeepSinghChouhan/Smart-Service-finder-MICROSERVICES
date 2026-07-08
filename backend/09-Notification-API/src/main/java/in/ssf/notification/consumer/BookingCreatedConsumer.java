package in.ssf.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import in.ssf.event.dto.BookingCreatedEvent;
import in.ssf.notification.service.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingCreatedConsumer {

    private final NotificationService notificationService;
    
    @PostConstruct
    public void init() {
        System.out.println("🔥 NOTIFICATION CONSUMER STARTED");
    }
    
    @KafkaListener(topics = "Booking-Topic",groupId = "notification-group")
    public void consume(BookingCreatedEvent event) {

        System.out.println("🔥 RAW MESSAGE RECEIVED");

        System.out.println("TITLE: " + event.getTitle());

        notificationService.handleBookingCreated(event);

    }
}