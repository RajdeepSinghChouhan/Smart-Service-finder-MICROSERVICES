package in.ssf.userservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.userservice.dto.UserInfoDto;
import in.ssf.userservice.service.UserServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "UserService Controller", description = "UserService Related APIs")
public class UserRestController {
	
	@Autowired
	private UserServiceService userService;
	
	@PostMapping("/data")
	@Operation(summary = "Save User Data")
	public UserInfoDto saveData(@Valid @RequestBody UserInfoDto request)
	{
		return userService.saveUserDetails(request);
	}
	
	@GetMapping("/data")
	@Operation(summary = "Get User Data")
	public UserInfoDto getData(@RequestHeader("X-User-Name") String username)
	{
		return userService.getUserData(username);
	}
	
	
	@GetMapping("/me/{userId}")
	@Operation(summary = "Save User Data By UserId")
	public UserInfoDto getDataByUserId(@PathVariable Long userId)
	{
		return userService.getUserDataByUserId(userId);
	}
	
	@PutMapping("/data")
	@Operation(summary = "Update User Data")
	public UserInfoDto updateData(@RequestBody UserInfoDto request,
							@RequestHeader("X-User-Name") String username )
	{
		return userService.updateUserDetails(request,username);
	}
}
