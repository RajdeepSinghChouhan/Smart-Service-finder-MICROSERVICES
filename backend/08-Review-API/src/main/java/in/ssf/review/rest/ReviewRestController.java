package in.ssf.review.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.event.dto.ReviewRequest;
import in.ssf.event.dto.ReviewResponse;
import in.ssf.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
@Tag(name = "ReviewService Controller", description = "ReviewService Related APIs")
public class ReviewRestController
{
	@Autowired
    private ReviewService service;
	
	@GetMapping("/test")
	public String test() {
	    return "Review API Working";
	}
	
	
    //add review by user
    @PostMapping("/data")
    @Operation(summary = "Create Review")
    public ResponseEntity<ReviewResponse> createReview(@RequestHeader("X-User-Id")Long userId,
    			@Valid @RequestBody ReviewRequest request)
    {
        return ResponseEntity.ok(service.createReview(userId,request));
    }
    
    //get provider review
    @GetMapping("/provider/{providerId}")
    @Operation(summary = "Get Provider Review")
    public ResponseEntity<List<ReviewResponse>> getProviderReviews(@PathVariable Long providerId)
    {
    	return ResponseEntity.ok(service.getReviewForProvider(providerId));
    }
    
    //get user review
    @GetMapping("/my")
    @Operation(summary = "Get User Review")
    public ResponseEntity<List<ReviewResponse>> getUserReviews(@RequestHeader("X-User-Id")Long userId)
    {
    	return ResponseEntity.ok(service.getReviewForUser(userId));
    }
    
    
    //get review of particular service
    @GetMapping("/service/{serviceId}")
    @Operation(summary = "Get Service Related Review")
    public ResponseEntity<List<ReviewResponse>> getServiceReview(@PathVariable Long serviceId)
    {
    	return ResponseEntity.ok(service.getReviewsOfService(serviceId));
    }
    
    //update review by user 
    @PutMapping("/update/{reviewId}")
    @Operation(summary = "Update Review Data")
    public ResponseEntity<ReviewResponse> updateReview(@RequestHeader("X-User-Id")Long userId,
    						   @PathVariable Long reviewId,
    						   @Valid @RequestBody ReviewRequest request)
    {
    	return ResponseEntity.ok(service.updateReviewByReviewId(reviewId, userId, request));	
    }
    
    @DeleteMapping("/delete/{reviewId}")
    @Operation(summary = "Delete Review")
    public ResponseEntity<String> deleteReview( @RequestHeader("X-User-Id") Long userId,
            									@PathVariable Long reviewId)
    {
        service.delteReview(userId,reviewId);
        return ResponseEntity.ok("Review Deleted");
    }
    
    
    @GetMapping("/provider/{providerId}/average-rating")
    @Operation(summary = "Get Average Rating of Provider")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long providerId)
    {
        return ResponseEntity.ok(service.getAverageRating(providerId));
    }
    
    
    @GetMapping("/provider/{providerId}/count")
    @Operation(summary = "Get Review Count")
    public ResponseEntity<Long> getReviewCount(@PathVariable Long providerId)
    {
        return ResponseEntity.ok(service.getReviewCount(providerId));
    }
}
