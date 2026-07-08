package in.ssf.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreatedEvent {

	private Long userId;
	
    private Long providerId;

    private String email;

    private String title;

    private String message;

}