package in.ssf.userservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ssf.userservice.model.UserService;

@Repository
public interface UserServiceRepo extends JpaRepository<UserService,Long>{
	
	Optional<UserService> findByUsername(String username);
}
