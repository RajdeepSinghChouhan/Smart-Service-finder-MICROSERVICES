package in.ssf.userservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExcpetionHandler {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(
	        UserNotFound ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            404,
	            "Not Found",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
