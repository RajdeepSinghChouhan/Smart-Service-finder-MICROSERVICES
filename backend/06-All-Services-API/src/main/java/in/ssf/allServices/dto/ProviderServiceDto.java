package in.ssf.allServices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderServiceDto {

    private Long providerId;

    private Long userId;

    private String businessName;

    private String description;

    private String experience;

    private Long categoryId;

    private Double rating = 0.0;

    private Boolean verified = false;
}

