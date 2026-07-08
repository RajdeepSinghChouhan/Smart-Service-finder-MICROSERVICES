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
public class BookingCreatedEvent {

    private Long bookingId;

    private Long userId;

    private String title;

    private String message;

}
