package in.ssf.event.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {

    private Long id;

    private String title;

    private String message;

    private String status;

    private Boolean isRead;

    private LocalDateTime createdAt;

}