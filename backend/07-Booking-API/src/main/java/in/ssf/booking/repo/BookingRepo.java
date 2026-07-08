package in.ssf.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ssf.booking.model.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long>
{
	public List<Booking> findByUserId(Long userId);	
	public List<Booking> findByProviderId(Long providerId);
}
