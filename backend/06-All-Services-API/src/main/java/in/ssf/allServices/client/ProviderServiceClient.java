package in.ssf.allServices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("05-Provider-Service-API")
public interface ProviderServiceClient {

	@GetMapping("/provider/getId")
	public Long getProviderIdByUserId(@RequestHeader("X-User-Id")Long userId);
}
