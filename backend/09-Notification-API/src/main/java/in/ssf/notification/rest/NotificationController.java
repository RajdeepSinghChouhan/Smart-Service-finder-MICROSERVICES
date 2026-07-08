package in.ssf.notification.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.notification.model.Notification;
import in.ssf.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "NotificationService Controller", description = "NotificationService Related APIs")
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Notification By UserId")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUserId(userId));
    }


    @GetMapping("/user/{userId}/unread-count")
    @Operation(summary = "Get Unread Notification Count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadCount(userId));
    }


    @PutMapping("/{id}/read")
    @Operation(summary = "Mark Notification As Read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {

        notificationService.markAsRead(id);

        return ResponseEntity.ok("Notification marked as read");
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Notification")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok("Notification deleted");
    }


    @PostMapping("/retry-failed")
    @Operation(summary = "Retry for Notification")
    public ResponseEntity<String> retryFailedNotifications() {

        notificationService.retryFailedNotifications();

        return ResponseEntity.ok("Retry process completed");
    }

}
