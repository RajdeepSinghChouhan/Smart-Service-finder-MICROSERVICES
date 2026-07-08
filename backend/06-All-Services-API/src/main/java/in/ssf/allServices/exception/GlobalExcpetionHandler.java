package in.ssf.allServices.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExcpetionHandler {

	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(
			ServiceNotFoundException ex,
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
	@ExceptionHandler(ProviderServiceUnavailableException.class)
	public ResponseEntity<ErrorResponse> handleProviderServiceUnavailable(
	        ProviderServiceUnavailableException ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.SERVICE_UNAVAILABLE.value(),
	            ex.getMessage(),
	            HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.SERVICE_UNAVAILABLE)
	            .body(error);
	}
}
