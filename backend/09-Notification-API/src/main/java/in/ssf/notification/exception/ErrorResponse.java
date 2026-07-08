package in.ssf.notification.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;

}