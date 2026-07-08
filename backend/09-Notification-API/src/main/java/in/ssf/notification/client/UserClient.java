package in.ssf.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.ssf.event.dto.UserDto;

@FeignClient(name = "04-User-Service-API")
public interface UserClient {

    @GetMapping("/user/me/{userId}")
    UserDto getUserById(@PathVariable Long userId);

}