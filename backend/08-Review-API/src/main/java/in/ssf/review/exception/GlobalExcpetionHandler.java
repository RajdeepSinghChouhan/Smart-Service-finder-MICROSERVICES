package in.ssf.review.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExcpetionHandler {

	@ExceptionHandler(ReviewNotFound.class)
	public ResponseEntity<ErrorResponse> handleReviewNotFound(
			ReviewNotFound ex,
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
	
	@ExceptionHandler(ReviewAlreadyExist.class)
	public ResponseEntity<ErrorResponse> handleReviewAlreadyExist(
			ReviewAlreadyExist ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            403,
	            "Review Already Exist",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	@ExceptionHandler(RatingNotInRange.class)
	public ResponseEntity<ErrorResponse> handleRatingNotInRange(
			RatingNotInRange ex,
	        HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            400,
	            "Rating Not In Range",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AllServiceUnavailableException.class)
	public ResponseEntity<ErrorResponse> handleAllServiceUnavailable(
			AllServiceUnavailableException ex,
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
