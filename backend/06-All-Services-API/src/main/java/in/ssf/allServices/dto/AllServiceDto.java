package in.ssf.allServices.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllServiceDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull
    private Long categoryId;

    private Long providerId;

    @NotNull
    private Boolean available = true;

    
    private LocalDateTime createdAt = LocalDateTime.now();
}
