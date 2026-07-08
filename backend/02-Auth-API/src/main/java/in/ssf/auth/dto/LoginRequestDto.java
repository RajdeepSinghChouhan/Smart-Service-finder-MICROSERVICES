package in.ssf.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login Request")
public class LoginRequestDto {

    @NotBlank
    @Schema(description = "User's full name", example = "John")
    private String username;

    @NotBlank
    @Schema(description = "User's email address", example = "john@gmail.com")
    private String password;
}
