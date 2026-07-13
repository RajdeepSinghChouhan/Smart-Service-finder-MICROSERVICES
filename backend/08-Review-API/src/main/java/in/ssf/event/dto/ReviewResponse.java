package in.ssf.event.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse
{
    private Long reviewId;

    private Long userId;
    
    private String userEmail;
    
    private String username;

    private Long providerId;

    private Long serviceId;

    private Integer rating;

    private String comment;

    private String serviceTitle;
    
    private Double servicePrice;
    
    private LocalDateTime createdAt;
}