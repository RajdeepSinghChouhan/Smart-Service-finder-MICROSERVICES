package in.ssf.auth.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
	
	@ExceptionHandler(InvalidCredentials.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(
			InvalidCredentials ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            401,
	            "Invalid Credentials",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(UserAlreadyExist.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExist(
			UserAlreadyExist ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            404,
	            "User Already Exist",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(AccountDisabled.class)
	public ResponseEntity<ErrorResponse> handleAccountDisabled(
			AccountDisabled ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            403,
	            "Account Disabled",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
}
