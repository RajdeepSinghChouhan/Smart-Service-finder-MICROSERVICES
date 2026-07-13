package in.ssf.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import in.ssf.booking.enums.BookingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {

	private Long id;
	
    private Long userId;
    
    private Long providerId;
    
    private Long serviceId;

    private LocalDate bookingDate;

    private LocalTime bookingTime;
    
    private BookingStatusEnum status = BookingStatusEnum.PENDING;

    private String address;

    private String notes;
    
    private String serviceTitle;
    
    private Double price;
    
    private LocalDateTime createdAt;
    
    private String username;
   
    private String userEmail;

}
