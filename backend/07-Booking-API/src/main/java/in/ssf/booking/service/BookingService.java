package in.ssf.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.booking.client.ProviderClient;
import in.ssf.booking.client.ServiceClient;
import in.ssf.booking.enums.BookingStatusEnum;
import in.ssf.booking.exception.AllServiceUnavailableException;
import in.ssf.booking.exception.BookingNotFound;
import in.ssf.booking.exception.ProviderServiceUnavailableException;
import in.ssf.booking.model.Booking;
import in.ssf.booking.repo.BookingRepo;
import in.ssf.event.dto.BookingCreatedEvent;
import in.ssf.event.dto.BookingRequestDto;
import in.ssf.event.dto.BookingResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

	@Autowired
	private BookingRepo bookingRepo;
	
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;
	
	@Autowired
	private ServiceClient serviceClient;
	
	@Autowired
	private ProviderClient providerClient;
	
	
	public BookingResponseDto  addBooking(BookingRequestDto request,Long userId)
	{
		log.info("Adding Booking for UserId {}",userId);
		
		Booking bookingFromDto = mapRequestDtoToObject(request);
		
		bookingFromDto.setUserId(userId);
		bookingFromDto.setStatus(BookingStatusEnum.PENDING);

		Long providerId = getProviderIdByServiceClient(request.getServiceId());

		bookingFromDto.setProviderId(providerId);
		
		//dto to object

		Booking booking = bookingRepo.save(bookingFromDto);
		
		BookingCreatedEvent event =
		        BookingCreatedEvent.builder()
		                .bookingId(booking.getId())
		                .userId(userId)
		                .title("Booking Confirmed")
		                .message("Your booking has been confirmed.")
		                .build();

		kafkaTemplate.send("Booking-Topic",event)
		 .whenComplete((res, ex) -> {
			 if (ex == null) {
		            log.info("Message Succefully Sent To Kafka");
		        } else {
		            log.error("Failed To Sent Message To Kafka");
		            ex.printStackTrace();
		        }
		 });
		
		//object to dto 
		return mapObjectToResponseDto(booking);
	}
	
	@Transactional
	public BookingResponseDto updateBooking( Long bookingId,BookingRequestDto request,Long userId)
	{
		log.info("Updating Booking Details For UserId {}",userId);
		
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(
				() -> new BookingNotFound("Booking Not Found for bookingId "+bookingId));
		
		Long providerId = getProviderIdByServiceClient(request.getServiceId());

		booking.setServiceId(request.getServiceId());
		booking.setBookingDate(request.getBookingDate());
		booking.setBookingTime(request.getBookingTime());
		booking.setAddress(request.getAddress());
		booking.setNotes(request.getNotes());
		booking.setProviderId(providerId);
		booking.setUserId(userId);

		Booking updatedBooking = bookingRepo.save(booking);		
		
		BookingCreatedEvent event =
		        BookingCreatedEvent.builder()
		                .bookingId(updatedBooking.getId())
		                .userId(userId)
		                .title("Booking Updated")
		                .message("Your booking has been Updated.")
		                .build();

		kafkaTemplate.send("Booking-Topic",event)
		 .whenComplete((res, ex) -> {
			 if (ex == null) {
		            log.info("Message Succefully Sent To Kafka");
		        } else {
		            log.error("Failed To Sent Message To Kafka");
		            ex.printStackTrace();
		        }
		 });
		
		return mapObjectToResponseDto(updatedBooking);
	}
	
	public List<BookingResponseDto> getAllBookings()
	{
		log.info("Getting All Bookings");
		
		List<Booking> all = bookingRepo.findAll();
		
		List<BookingResponseDto> dtoList = new ArrayList<>();
		
		for(Booking booking : all)
		{
			BookingResponseDto bookingFromDto = mapObjectToResponseDto(booking);
			
			dtoList.add(bookingFromDto);
		}
		
		return dtoList;
	}
	
	public List<BookingResponseDto> getBookingOfUser(Long userId)
	{
		log.info("Getting All Bookings Of User by UserId {}",userId);
		
		List<Booking> all = bookingRepo.findByUserId(userId);
		
		List<BookingResponseDto> dtoList = new ArrayList<>();
		
		for(Booking booking : all)
		{
			BookingResponseDto bookingFromDto = mapObjectToResponseDto(booking);
			
			dtoList.add(bookingFromDto);
		}
		
		return dtoList;
	}
	
	public List<BookingResponseDto> getBookingOfProvider(Long userId)
	{
	    Long providerId = getProviderIdByProviderClient(userId);

	    log.info("Getting All Bookings Of Provider By ProviderId {}",providerId);
	    
	    List<Booking> all = bookingRepo.findByProviderId(providerId);
	    
	    List<BookingResponseDto> dtoList = new ArrayList<>();
		
		for(Booking booking : all)
		{
			BookingResponseDto bookingFromDto = mapObjectToResponseDto(booking);
			
			dtoList.add(bookingFromDto);
		}
		
		return dtoList;
	} 
	
	//accept by provider
	@Transactional
	public String acceptBooking(Long bookingId)
	{
		log.info("Accepting Booking Whose BookingId {}",bookingId);
		
		updateStatus(bookingId,BookingStatusEnum.ACCEPTED,"Booking Accepted","Your booking has been Accepted.");
	
		return "Booking ACCEPTED";
	}
	
	//rejected by provider
	@Transactional
	public String rejectBooking(Long bookingId)
	{	
		log.info("Rejecting Booking Whose BookingId {}",bookingId);
		
		updateStatus(bookingId,BookingStatusEnum.REJECTED,"Booking Rejected","Your booking has been Rejected.");
		
		return "Booking REJECTED";
	}
	
	//cancel by user
	@Transactional
	public String cancelBooking(Long bookingId)
	{
		log.info("Canceling Booking Whose BookingId {}",bookingId);
		
		updateStatus(bookingId,BookingStatusEnum.CANCELLED,"Booking Cancelled","Your booking has been Cancelled.");
		
		return "Booking CANCELLED";
	}
	
	//complete marked by provider
	@Transactional
	public String completeBooking(Long bookingId)
	{
		log.info("Marking Booking As Completed Whose BookingId {}",bookingId);
		
		updateStatus(bookingId,BookingStatusEnum.COMPLETED,"Booking Completed","Your booking has been Completed.");
		
		return "Booking COMPLETED";
	}
	
	private void updateStatus(Long bookingId,BookingStatusEnum status,String title,String message) 
	{
		
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(
				()-> new BookingNotFound("No booking found for booking id "+bookingId));
		
		booking.setStatus(status);
		
		Booking save = bookingRepo.save(booking);
		
		
		BookingCreatedEvent event =
		        BookingCreatedEvent.builder()
		                .bookingId(booking.getId())
		                .userId(save.getUserId())
		                .title(title)
		                .message(message)
		                .build();

		kafkaTemplate.send("Booking-Topic",event)
			.whenComplete((res, ex) -> {
		        if (ex == null) {
		            log.info("Message Succefully Sent To Kafka");
		        } else {
		            log.error("Failed To Sent Message To Kafka");
		            ex.printStackTrace();
		        }
		 });
	}
	
	
	public Booking mapRequestDtoToObject(BookingRequestDto dto) {

		 if (dto == null) {
		        return null;
		    }
	    Booking booking = new Booking();

	    booking.setServiceId(dto.getServiceId());
	    booking.setBookingDate(dto.getBookingDate());
	    booking.setBookingTime(dto.getBookingTime());
	    booking.setAddress(dto.getAddress());
	    booking.setNotes(dto.getNotes());

	    return booking;
	}
	
	public BookingResponseDto mapObjectToResponseDto(Booking booking) {

		 if (booking == null) {
		        return null;
		    }
	    BookingResponseDto dto = new BookingResponseDto();

	    dto.setId(booking.getId());
	    dto.setUserId(booking.getUserId());
	    dto.setProviderId(booking.getProviderId());
	    dto.setServiceId(booking.getServiceId());
	    dto.setBookingDate(booking.getBookingDate());
	    dto.setBookingTime(booking.getBookingTime());
	    dto.setAddress(booking.getAddress());
	    dto.setNotes(booking.getNotes());
	    dto.setStatus(booking.getStatus());

	    return dto;
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
	
	@CircuitBreaker(name = "providerService", fallbackMethod = "providerFallback")
	Long getProviderIdByProviderClient(Long userId)
	{
		return providerClient.getProviderIdByUserId(userId);
	}
	
	public Long providerFallback(Long userId, Exception ex) {
		throw new ProviderServiceUnavailableException(
	            "Provider Service is currently unavailable. Please try again later.");
	}
}

