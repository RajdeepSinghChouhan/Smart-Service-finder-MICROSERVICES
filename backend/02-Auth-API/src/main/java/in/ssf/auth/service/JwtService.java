package in.ssf.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ssf.auth.dto.AuthResponseDto;
import in.ssf.auth.dto.LoginRequestDto;
import in.ssf.auth.dto.RegisterRequestDto;
import in.ssf.auth.model.User;
import in.ssf.auth.repo.UserRepository;
import in.ssf.auth.util.JwtUtil;

@Service
public class JwtService {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	public String register(RegisterRequestDto request)
	{
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(
	            passwordEncoder.encode(request.getPassword())
	    );
		user.setEnabled(true);
		user.setRole(request.getRole());

		if(userRepo.findByUsername(request.getUsername()).isPresent())
		{
		    throw new RuntimeException("Username already exists");
		}
		userRepo.save(user);
		
		return "Registered";
	}
	
	
	public AuthResponseDto login(LoginRequestDto request)
	{
		System.out.println("comes inside method login sevice with username  = "+request.getUsername());
		
		User user = (User) userRepo.findByUsername(request.getUsername())
	            .orElseThrow(() ->
                new RuntimeException("User not found"));
		
		if(!user.isEnabled())
		{
		    throw new RuntimeException("Account disabled");
		}
		
		System.out.println("User is valid username == "+user.getUsername());
	
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
			 throw new RuntimeException("Invalid Credentials");
		 }
	}
}
