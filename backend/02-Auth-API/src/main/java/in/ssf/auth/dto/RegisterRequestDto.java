package in.ssf.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Register Request")
public class RegisterRequestDto 
{
	@NotBlank
    @Size(min = 3, max = 50)
	@Schema(description = "User's full name", example = "John")
    private String username;

	@NotBlank
	@Email
	private String email;
	
    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(description = "User's Password", example = "username@123")
    private String password;

    @NotBlank
    @Pattern(regexp = "USER|PROVIDER")
    @Schema(description = "Role Of the User", example = "USER|PROVIDER")
    private String role;
}
