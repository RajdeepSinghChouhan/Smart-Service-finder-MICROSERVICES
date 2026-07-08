package in.ssf.notification.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ssf.notification.enums.NotificationStatus;
import in.ssf.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    long countByUserIdAndIsReadFalse(Long userId);

    List<Notification> findByStatus(NotificationStatus status);

}