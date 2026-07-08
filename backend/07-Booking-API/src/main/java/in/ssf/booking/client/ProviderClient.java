package in.ssf.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("05-Provider-Service-API")
public interface ProviderClient {

	@GetMapping("/provider/getId")
    public Long getProviderIdByUserId(Long userId);
	
}
