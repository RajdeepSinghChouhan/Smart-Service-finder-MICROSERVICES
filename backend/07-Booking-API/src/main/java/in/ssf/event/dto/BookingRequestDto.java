package in.ssf.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    
	@NotNull(message = "Service Id is required")
    @Positive(message = "Service Id must be greater than 0")
    @Schema(description = "Service Id", example = "401")
    private Long serviceId;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date cannot be in the past")
    @Schema(description = "Booking Date", example = "2026-06-28")
    private LocalDate bookingDate;

    @NotNull(message = "Booking time is required")
    @Schema(description = "Booking Time", example = "20:02:15")
    private LocalTime bookingTime;
    
    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters")
    @Schema(description = "Booking Address", example = "121 BaseA, Los Angeles")
    private String address;

    @NotBlank(message = "Notes are required")
    @Size(min = 5, max = 500, message = "Notes must be between 5 and 500 characters")
    @Schema(description = "Additional Booking Notes", example = "Refrigerator is not cooling properly.")
    private String notes;
	
}
