package in.ssf.allServices.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllServiceResponseDto {

	private Long serviceId;
	
	private Long providerId;
	
	private Long providerUserId;

    //provider businessname
    private String businessName;

    //provider description  
    private String description;

    //provider experience 
    private String experience;

    //service categoryId
    private Long categoryId;

    //provider rating 
    private Double rating = 0.0;

    private Boolean verified = false;

    //service title
    private String title;
    
    //service price
    private Double price;

    //service availability
    private Boolean available = true;
    
    //service created at
    private LocalDateTime createdAt = LocalDateTime.now();
}
