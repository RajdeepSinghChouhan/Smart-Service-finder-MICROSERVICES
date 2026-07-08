package in.ssf.notification.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ssf.event.dto.ReviewCreatedEvent;
import in.ssf.notification.service.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCreatedConsumer {

    private final NotificationService notificationService;

    
    @PostConstruct
    public void init() {
        System.out.println("🔥 Review Notification CONSUMER STARTED");
    }
    
    @KafkaListener(topics = "Review-Topic",groupId = "notification-group")
    public void consume(ReviewCreatedEvent event) 
    {

    		System.out.println("🔥 RAW MESSAGE RECEIVED");

        System.out.println("TITLE: " + event.getTitle());

        notificationService.handleReviewCreated(event);
        
    }

}