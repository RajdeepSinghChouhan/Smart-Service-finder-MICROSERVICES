package in.ssf.userservice.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "User Request Object")
public class UserInfoDto {

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "User's name", example = "John")
    private String username;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "User's Full name", example = "JohnDoe")
    private String fullName;

    @NotBlank
    @Email
    @Schema(description = "User's Email", example = "John@gmail.com")
    
    private String email;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits")
    @Schema(description = "User's Contact Number", example = "9876543210")
    private String phone;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "User's Address", example = "4-158/9, 5th Cross RoadSainikpuri, Secunderabad, Telangana500094")
    private String address;

    private String profileImage;
}

