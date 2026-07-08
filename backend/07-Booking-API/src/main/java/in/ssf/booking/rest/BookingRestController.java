package in.ssf.booking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.booking.service.BookingService;
import in.ssf.event.dto.BookingRequestDto;
import in.ssf.event.dto.BookingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
@Tag(name = "BookingService Controller", description = "BookingService Related APIs")
public class BookingRestController {

	@Autowired
	private BookingService service;
	
	@PostMapping("/data")
	@Operation(summary = "Save Booking Data")
	public BookingResponseDto addBooking(@Valid @RequestBody BookingRequestDto request,@RequestHeader("X-User-Id") Long userId)
	{
		return service.addBooking(request,userId);
	}
	
	@PutMapping("/data/{bookingId}")
	@Operation(summary = "Update Booking Data")
	public BookingResponseDto updateBooking( @PathVariable Long bookingId,@Valid @RequestBody BookingRequestDto request,@RequestHeader("X-User-Id") Long userId)
	{
		return service.updateBooking(bookingId,request,userId);
	}
	
	@GetMapping("/allBooking")
	@Operation(summary = "Get All Booking")
	public List<BookingResponseDto> getAllBooking()
	{
		return service.getAllBookings();
	}
	
	@GetMapping("/userBooking")
	@Operation(summary = "Save Provider Data")
	public List<BookingResponseDto> getBookingsOfUser(@RequestHeader("X-User-Id") Long userId)
	{
		return service.getBookingOfUser(userId);
	}
                                                                                                       
	@GetMapping("/providerBooking")  
	@Operation(summary = "Getting Booking Of Provider")
	public List<BookingResponseDto> getBookingsOfProvider(@RequestHeader("X-User-Id") Long userId)
	{
		return service.getBookingOfProvider(userId);
	}
	
	@PutMapping("/{bookingId}/accept")
	@Operation(summary = "Accept Booking")
	public String acceptBooking(@PathVariable Long bookingId)
	{
		return service.acceptBooking(bookingId);
	}
	
	@PutMapping("/{bookingId}/reject")
	@Operation(summary = "Reject Booking")
	public String rejectBooking(@PathVariable Long bookingId)
	{
		return service.rejectBooking(bookingId);
	}
	
	@PutMapping("/{bookingId}/cancel")
	@Operation(summary = "Cancel Booking")
	public String cancelBooking(@PathVariable Long bookingId)
	{
		return service.cancelBooking(bookingId);
	}
	
	@PutMapping("/{bookingId}/completed")
	@Operation(summary = "Complete Booking")
	public String completedBooking(@PathVariable Long bookingId)
	{
		return service.completeBooking(bookingId);
	}
	
}
