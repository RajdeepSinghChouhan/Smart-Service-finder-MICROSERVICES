package in.ssf.review.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ssf.review.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>
{
    List<Review> findByProviderId(Long providerId);

    List<Review> findByServiceId(Long serviceId);

    List<Review> findByUserId(Long userId);
    
    Optional<Review> findByUserIdAndServiceId(Long userId,Long serviceId);
}
