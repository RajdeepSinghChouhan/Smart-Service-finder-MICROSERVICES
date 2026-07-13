package in.ssf.review.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.event.dto.ReviewCreatedEvent;
import in.ssf.event.dto.ReviewRequest;
import in.ssf.event.dto.ReviewResponse;
import in.ssf.event.dto.ServiceDto;
import in.ssf.event.dto.UserDto;
import in.ssf.review.client.ServiceClient;
import in.ssf.review.client.UserClient;
import in.ssf.review.exception.AllServiceUnavailableException;
import in.ssf.review.exception.RatingNotInRange;
import in.ssf.review.exception.ReviewAlreadyExist;
import in.ssf.review.exception.ReviewNotFound;
import in.ssf.review.model.Review;
import in.ssf.review.repo.ReviewRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService
{
	@Autowired
    private  ReviewRepository repository;

	@Autowired
	private KafkaTemplate<String,ReviewCreatedEvent> kafkaTemplate;
	
	@Autowired
    private  ServiceClient serviceClient;
	
	@Autowired
    private  UserClient usercleint;
	
    public ReviewResponse createReview(Long userId,ReviewRequest request)
    {
    		
    		log.info("Creating Review For UserId {}",userId);
    		
        Long providerId = serviceClient.getProviderIdByServiceId(request.getServiceId());

        Review review = Review.builder()
                        .userId(userId)
                        .providerId(providerId)
                        .serviceId(request.getServiceId())
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .createdAt(LocalDateTime.now())
                        .build();
        
        if(request.getRating() < 1 || request.getRating() > 5)
        {
            throw new RatingNotInRange(
                    "Rating must be between 1 and 5");
        }

        Optional<Review> existing =
        		repository
        		.findByUserIdAndServiceId(
        		        userId,
        		        request.getServiceId());
        
        if(existing.isPresent())
        {
        		throw new ReviewAlreadyExist("Review already exists");
        }
        
        Review save = repository.save(review);
        
        //sending message to kafka 
        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .providerId(providerId)
                .userId(userId)
                .title("Review Saved")
                .message("Your Review is successful added")
                .build();
        
        kafkaTemplate.send("Review-Topic",event)
	        .whenComplete((res, ex) -> {
		        if (ex == null) {
		        		log.info("Event Sent To Kafka Successs");
		        } else {
		        		log.info("Failed To Sent Event To Kafka");
		            ex.printStackTrace();
		        }
		 });
        
        ServiceDto dto =
                serviceClient.getServiceById(
                        save.getServiceId());
        
        UserDto userDto = usercleint.getUserById(userId);

        return map(save,dto,userDto);
    }

    private ReviewResponse map(Review review,ServiceDto dto, UserDto userDto)
    {	
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUserId())
                .userEmail(userDto.getEmail())
                .username(userDto.getUsername())
                .providerId(review.getProviderId())
                .serviceId(review.getServiceId())
                .serviceTitle(dto.getTitle())
                .rating(review.getRating())
                .servicePrice(dto.getPrice())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
    
    public List<ReviewResponse> getReviewForProvider(Long providerId)
    {
    		
    		log.info("Getting Review For Provider By ProviderId {}",providerId);
    		
	    	List<Review> byProviderId = repository.findByProviderId(providerId);
	    	List<ReviewResponse> list = new ArrayList<>();
	    	for(Review review : byProviderId)
	    	{
	    		ServiceDto dto =
		                serviceClient.getServiceById(
		                        review.getServiceId());
	    		
	    		UserDto userDto = usercleint.getUserById(review.getUserId());
	    		
	    		ReviewResponse map = map(review,dto,userDto);
	    		list.add(map);
	    	}
	    	return list;
    }
    
    public List<ReviewResponse> getReviewForUser(Long userId)
    {
		log.info("Getting Review For User By UserId {}",userId);
		
	    	List<Review> byUserId = repository.findByUserId(userId);
	    	List<ReviewResponse> list = new ArrayList<>();
	    	for(Review review : byUserId)
	    	{
	    		ServiceDto dto =
                serviceClient.getServiceById(
                        review.getServiceId());
	    		
	    		UserDto userDto = usercleint.getUserById(userId);
	    		
	    		ReviewResponse map = map(review,dto,userDto);
	    		list.add(map);
	    	}
	    	return list;
    }
    
    public List<ReviewResponse> getReviewsOfService(Long serviceId)
    {
		log.info("Getting Review Of Service By ServiceId {}",serviceId);
		
	    	List<Review> byServiceId = repository.findByServiceId(serviceId);
	    	List<ReviewResponse> list = new ArrayList<>();
	    	for(Review review : byServiceId)
	    	{
	    		ServiceDto dto =
		                serviceClient.getServiceById(
		                        review.getServiceId());
	    		
	    		
	    		UserDto userDto = usercleint.getUserById(review.getUserId());
	    		
	    		
	    		ReviewResponse map = map(review,dto,userDto);
	    		list.add(map);
	    	}
	    	return list;
    }
    
    //updateReview
    @Transactional
    public ReviewResponse updateReviewByReviewId(Long reviewId,Long userId,ReviewRequest request)
    {
		log.info("Updating Review By ReviewId {}",reviewId);
		
	    	Review review = repository.findById(reviewId).orElseThrow
	    			( ()-> new ReviewNotFound("No Review Found For ReviewId "+reviewId));
	    	
	    	Review editedReview;
	    	
	    	if(review.getUserId().equals(userId))
	    	{
	    		if(request.getRating() < 1 || request.getRating() > 5)
	    		{
	    		    throw new RatingNotInRange(
	    		            "Rating must be between 1 and 5");
	    		}
	    		review.setComment(request.getComment());
	    		review.setRating(request.getRating());
	    		editedReview = repository.save(review);
	    		
	    		Review save = repository.save(review);
	                    
	        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
	                .providerId(save.getProviderId())
	                .userId(userId)
	                .title("Review updated")
	                .message("Your Review is successful updated")
	                .build();
	        
	        //message sent to the kafka where topic name is Review-Topic and data is event 
	        kafkaTemplate.send("Review-Topic",event)
			        .whenComplete((res, ex) -> {
				        if (ex == null) {
			        		log.info("Event Sent To Kafka Successs");
			        } else {
			        		log.info("Failed To Sent Event To Kafka");
			            ex.printStackTrace();
			        }
			 });
	    	}
	    	else
	    	{
	    		throw new ReviewNotFound("No Review Match To Update ");
	    	}
    	
	    	ServiceDto dto =
	                serviceClient.getServiceById(
	                		editedReview.getServiceId());
	    	
	    	UserDto userDto = usercleint.getUserById(userId);
	    	
		return map(editedReview,dto,userDto);
    }
    
    //Delete Review
    @Transactional
    public String delteReview(Long userId,Long reviewId)
    {
    		log.info("Deleting Review Of ReviewId {} By UserId {}", reviewId, userId);

    		
	    	Review review = repository.findById(reviewId).orElseThrow
	    			( ()-> new RuntimeException("No Review Found For ReviewId "+reviewId));
	    	
	    	if(!review.getUserId().equals(userId))
	    	{
	    		throw new ReviewNotFound("No Review Match To Delete");
	    	}
	    	Long providerId = review.getProviderId();
	    	
	    	repository.delete(review);
	    	
        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .providerId(providerId)
                .userId(userId)
                .title("Review Deleted")
                .message("Your Review is successful Deleted")
                .build();
        
        //message sent to the kafka where topic name is Review-Topic and data is event 
        kafkaTemplate.send("Review-Topic",event)
		        .whenComplete((res, ex) -> {
			        if (ex == null) {
		        		log.info("Event Sent To Kafka Successs");
		        } else {
		        		log.info("Failed To Sent Event To Kafka");
		            ex.printStackTrace();
		        }
		 });
	        
	    	return "Review Delted";
    }
    
    public Double getAverageRating(Long providerId)
    {
    		log.info("Getting Average Rating for Provider By ProviderId {}",providerId);
    	
        List<Review> reviews =
                repository.findByProviderId(providerId);

        if(reviews.isEmpty())
        {
            return 0.0;
        }

        double total = 0;

        for(Review review : reviews)
        {
            total += review.getRating();
        }

        return total / reviews.size();
    }
    
    public Long getReviewCount(Long providerId)
    {
    		log.info("Getting Review Count For Provider By ProviderId {} ",providerId);
    		
        List<Review> reviews = repository.findByProviderId(providerId);

        return (long) reviews.size();
    }
    
    @CircuitBreaker(name = "allService", fallbackMethod = "allServiceFallback")
	Long getProviderIdByServiceClient(Long serviceId)
	{
		return serviceClient.getProviderIdByServiceId(serviceId);
	}
	
	public Long allServiceFallback(Long serviceId, Exception ex) {
		throw new AllServiceUnavailableException(
	            "AllService Service is currently unavailable. Please try again later.");
	}
}