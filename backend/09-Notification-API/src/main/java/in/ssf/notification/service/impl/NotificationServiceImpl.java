
package in.ssf.notification.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ssf.event.dto.BookingCreatedEvent;
import in.ssf.event.dto.PasswordResetEvent;
import in.ssf.event.dto.ReviewCreatedEvent;
import in.ssf.event.dto.UserDto;
import in.ssf.notification.client.UserClient;
import in.ssf.notification.enums.NotificationStatus;
import in.ssf.notification.enums.NotificationType;
import in.ssf.notification.exception.UserServiceNotWorkingException;
import in.ssf.notification.model.Notification;
import in.ssf.notification.repo.NotificationRepository;
import in.ssf.notification.service.EmailService;
import in.ssf.notification.service.NotificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Autowired
    private final UserClient userClient;
    
    @Override
    public void handleBookingCreated(BookingCreatedEvent event) 
    {

    	   // 1. FETCH USER
    		log.info("Handling Booking Created Event For Notification");
    		
        UserDto user = getUserByIdByUserClient(event.getUserId());

        String email = user.getEmail();
        
        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .email(email)
                .title(event.getTitle())
                .message(event.getMessage())
                .type(NotificationType.EMAIL)
                .status(NotificationStatus.PENDING)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        try {

            emailService.sendEmail(
                    email,
                    event.getTitle(),
                    event.getMessage());

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {

        		log.error("Failed to process the Notification Sending Request",e);
            notification.setStatus(NotificationStatus.FAILED);
        }
        
        log.info("Notification Saved");

        notificationRepository.save(notification);
    }
    	
    @Override
    public void handleReviewCreated(ReviewCreatedEvent event) 
    {

    		// 1. FETCH USER
    		log.info("Handling Review Created Event For Notification");
        UserDto user = getUserByIdByUserClient(event.getUserId());

        String email = user.getEmail();
        
        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .email(email)
                .title(event.getTitle())
                .message(event.getMessage())
                .type(NotificationType.EMAIL)
                .status(NotificationStatus.PENDING)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        try {

            emailService.sendEmail(
                    email,
                    event.getTitle(),
                    event.getMessage());

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {

            notification.setStatus(NotificationStatus.FAILED);
        }
        
        log.info("Notification Saved");

        notificationRepository.save(notification);

    }

    @Override
    public void handlePasswordReset(PasswordResetEvent event) 
    {

    		log.info("Handling Password Reset Event For Notification");
        String body = "Your OTP is : " + event.getOtp();
        
        // 1. FETCH USER
        UserDto user = getUserByIdByUserClient(event.getUserId());

        String email = user.getEmail();

        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .email(email)
                .title(event.getTitle())
                .message(body)
                .type(NotificationType.EMAIL)
                .status(NotificationStatus.PENDING)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        try {

            emailService.sendEmail(
                    email,
                    event.getTitle(),
                    body);

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {

            notification.setStatus(NotificationStatus.FAILED);
        }

        log.info("Notification Saved");
        notificationRepository.save(notification);
    }
    
    @Override
    public List<Notification> getNotificationsByUserId(Long userId) 
    {
    		log.info("Getting Notification Count By UserId {}",userId);
    		
        return notificationRepository.findByUserId(userId);

    }
    
    @Override
    public long getUnreadCount(Long userId) 
    {
    		log.info("Getting Notification UnRead Count By UserId {}",userId);
    		
        return notificationRepository.countByUserIdAndIsReadFalse(userId);

    }
    
    @Override
    public void markAsRead(Long notificationId) 
    {
    		log.info("Marking Notification As Read");
    		
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));
        
        
        notification.setIsRead(true);

        notificationRepository.save(notification);

    }
    
    @Override
    public void deleteNotification(Long notificationId) 
    {
    		log.info("Delete Notification");
    		
        notificationRepository.deleteById(notificationId);

    }
    
    @Override
    public void retryFailedNotifications() 
    {

    		log.info("Retrying For Failed Notification");
        List<Notification> failedNotifications =
                notificationRepository.findByStatus(NotificationStatus.FAILED);

        for (Notification notification : failedNotifications) {

            try {

                emailService.sendEmail(
                        notification.getEmail(),
                        notification.getTitle(),
                        notification.getMessage());

                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());

                notificationRepository.save(notification);

            } catch (Exception e) {

            		log.error("Retry failed",e);
            }
        }
    }
    
    @CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
    UserDto getUserByIdByUserClient(Long userId)
    {
    		return userClient.getUserById(userId);
    }
    
    public UserDto userServiceFallback(Long userId, Exception ex) {
        throw new UserServiceNotWorkingException(
                "User Service is currently unavailable. Please try again later.");
    }
    
    
}