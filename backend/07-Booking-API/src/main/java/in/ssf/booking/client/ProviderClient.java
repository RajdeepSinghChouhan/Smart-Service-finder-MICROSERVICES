package in.ssf.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("05-Provider-Service-API")
public interface ProviderClient {

	@GetMapping("/provider/getId")
    Long getProviderIdByUserId(
            @RequestHeader("X-User-Id") Long userId);
	
}
