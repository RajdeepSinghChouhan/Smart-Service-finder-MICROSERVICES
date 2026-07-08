package in.ssf.provider.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
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
@Schema(description = "Booking Update Object")
public class ProviderServiceDto {

	@Schema(description = "Provider Id", example = "101")
    private Long id;

	@Schema(description = "User Id", example = "201")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "BusinessName ", example = "Plumber|Electrcian")
    private String businessName;

    @NotBlank
    @Size(min = 10, max = 1000)
    @Schema(description = "Description About Provider", example = "A Proffessional Plumber")
    private String description;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Experince in year Or Month", example = "1Year|4Month")
    private String experience;

    @NotNull
    @Schema(description = "Category Id Of the Service", example = "301")
    private Integer categoryId;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Schema(description = "Rating From 0 to 5", example = "1Year|4Month")
    private Double rating = 0.0;

    @Schema(description = "Varification Of Provider", example = "True|False")
    private Boolean verified = false;
}
