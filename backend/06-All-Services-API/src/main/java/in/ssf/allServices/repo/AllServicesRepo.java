package in.ssf.allServices.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ssf.allServices.model.AllService;

public interface AllServicesRepo extends JpaRepository<AllService,Long>{

	List<AllService> findAllByProviderId(Long providerId);
}
