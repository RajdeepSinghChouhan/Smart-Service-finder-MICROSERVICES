package in.ssf.notification.service;

import java.util.List;

import in.ssf.event.dto.BookingCreatedEvent;
import in.ssf.event.dto.PasswordResetEvent;
import in.ssf.event.dto.ReviewCreatedEvent;
import in.ssf.notification.model.Notification;

public interface NotificationService {

    void handleBookingCreated(BookingCreatedEvent event);

    void handleReviewCreated(ReviewCreatedEvent event);

    void handlePasswordReset(PasswordResetEvent event);


    List<Notification> getNotificationsByUserId(Long userId);

    long getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);

    void retryFailedNotifications();

}