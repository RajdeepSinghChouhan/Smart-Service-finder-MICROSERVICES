package in.ssf.allServices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import in.ssf.allServices.dto.ProviderServiceDto;

@FeignClient("05-Provider-Service-API")
public interface ProviderServiceClient {

	@GetMapping("/provider/getId")
	public Long getProviderIdByUserId(@RequestHeader("X-User-Id")Long userId);
	
	@GetMapping("/provider/data/{id}")
	public ProviderServiceDto getProviderDetailsById(@PathVariable Long  id);
}
