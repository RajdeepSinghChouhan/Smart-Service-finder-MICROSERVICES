package in.ssf.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {

    private Long userId;
    private String email;
    private String username;
    private String role;

    // getters setters
}