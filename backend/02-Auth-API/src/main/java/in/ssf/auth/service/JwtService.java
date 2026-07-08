package in.ssf.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ssf.auth.dto.AuthResponseDto;
import in.ssf.auth.dto.LoginRequestDto;
import in.ssf.auth.dto.RegisterRequestDto;
import in.ssf.auth.dto.RegisterResponseDto;
import in.ssf.auth.exception.AccountDisabled;
import in.ssf.auth.exception.InvalidCredentials;
import in.ssf.auth.exception.UserAlreadyExist;
import in.ssf.auth.exception.UserNotFound;
import in.ssf.auth.model.User;
import in.ssf.auth.repo.UserRepository;
import in.ssf.auth.util.JwtUtil;
import in.ssf.event.dto.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Autowired
	 private KafkaProducer producer;
	
	@Autowired
	private UserRepository userRepo;
	
	public RegisterResponseDto register(RegisterRequestDto request)
	{
		if(userRepo.findByUsername(request.getUsername()).isPresent())
		{
			throw new UserAlreadyExist("User already exists");
		}
		
		log.info("Registering the User whose username {} ", request.getUsername());
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(
	            passwordEncoder.encode(request.getPassword())
	    );
		user.setEnabled(true);
		user.setRole(request.getRole());

		
		User savedUser = userRepo.save(user);

		//Kafka event generating to save user service details just after registration 
		UserRegisteredEvent event = new UserRegisteredEvent();
		event.setUserId(savedUser.getId());
		event.setUsername(savedUser.getUsername());
		event.setRole(savedUser.getRole());

		producer.publish(event);
        
		RegisterResponseDto response = new RegisterResponseDto();
		response.setUsername(user.getUsername());
		response.setRole(user.getRole());
		
		return response;
	}
	
	
	public AuthResponseDto login(LoginRequestDto request)
	{
		
		log.info("Validating User whose username {} ", request.getUsername());
		
		User user = (User) userRepo.findByUsername(request.getUsername())
	            .orElseThrow(() ->
                new UserNotFound("User not found"));
		
		if(!user.getEnabled())
		{
		    throw new AccountDisabled("Account disabled");
		}
		
		log.info("User is valid whose username is {}",user.getUsername());
		
		boolean isMatch = passwordEncoder.matches(
	            request.getPassword(),
	            user.getPassword()
	    );
		if(isMatch)
		 {
			String token = jwtUtil.generateToken(user.getUsername(), user.getRole(),user.getId());
			AuthResponseDto response = new AuthResponseDto(token);
			response.setToken(token);
			return response;
		 }
		 else
		 {
			 throw new InvalidCredentials("Invalid Username or Password");
		 }
	}
}
