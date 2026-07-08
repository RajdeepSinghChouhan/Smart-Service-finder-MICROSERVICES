package in.ssf.userservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.userservice.dto.UserInfoDto;
import in.ssf.userservice.exception.UserNotFound;
import in.ssf.userservice.model.UserService;
import in.ssf.userservice.repo.UserServiceRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class UserServiceService {
	
	private final UserServiceRepo userRepo;

	public UserServiceService(UserServiceRepo userRepo) {
	    this.userRepo = userRepo;
	}
	
	public UserInfoDto saveUserDetails(UserInfoDto dto)
	{
		UserService userService = new UserService();
		userService.setUsername(dto.getUsername());
		userService.setFullName(dto.getFullName());
		userService.setEmail(dto.getEmail());
		userService.setAddress(dto.getAddress());
		userService.setPhone(dto.getPhone());
		userService.setProfileImage(dto.getProfileImage());
		
		
		log.info("Saving User Details whose username {} ", dto.getUsername());
		
		
		return dto ;
	}
	
	public UserInfoDto getUserData(String username)
	{
		log.info("Getting User Details for username {}", username);
		
		UserService userService = userRepo.findByUsername(username)
									.orElseThrow(()->
									new UserNotFound("User not Found")
											);
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setFullName(userService.getFullName());
		userInfoDto.setEmail(userService.getEmail());
		userInfoDto.setAddress(userService.getAddress());
		userInfoDto.setPhone(userService.getPhone());
		userInfoDto.setProfileImage(userService.getProfileImage());
		userInfoDto.setUsername(userService.getUsername());
		return userInfoDto;
	}
	
	public UserInfoDto getUserDataByUserId(Long userId)
	{
	
		log.info("Getting User Details for userId {}", userId);
		
		UserService userService = userRepo.findById(userId) 
									.orElseThrow(()->
									new UserNotFound("User not Found")
											);
		
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setFullName(userService.getFullName());
		userInfoDto.setEmail(userService.getEmail());
		userInfoDto.setAddress(userService.getAddress());
		userInfoDto.setPhone(userService.getPhone());
		userInfoDto.setProfileImage(userService.getProfileImage());
		userInfoDto.setUsername(userService.getUsername());
		
		return userInfoDto;
	}
	
	@Transactional
	public UserInfoDto updateUserDetails(UserInfoDto dto,String username)
	{
		log.info("Updating User Details for username {}", username);
		
		UserService userService = userRepo.findByUsername(username)
									.orElseThrow(()->
									new UserNotFound("User not Found")
											);
											
		if(dto.getFullName() != null)
			userService.setFullName(dto.getFullName());

		if(dto.getEmail() != null)
			userService.setEmail(dto.getEmail());

		if(dto.getPhone() != null)
			userService.setPhone(dto.getPhone());

		if(dto.getProfileImage() != null)
			userService.setProfileImage(dto.getProfileImage());
		
		if(dto.getAddress()!=null)
			userService.setAddress(dto.getAddress());
		
		userRepo.save(userService);
		
		log.info("User Details Updated");
		
		return dto;
	}
}
