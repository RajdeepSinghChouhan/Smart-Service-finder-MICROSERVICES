package in.ssf.provider.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ssf.provider.model.ProviderService;

@Repository
public interface ProviderServiceRepo extends JpaRepository<ProviderService,Integer>{
	
	Optional<ProviderService> findByUserId(Long userId);
}
