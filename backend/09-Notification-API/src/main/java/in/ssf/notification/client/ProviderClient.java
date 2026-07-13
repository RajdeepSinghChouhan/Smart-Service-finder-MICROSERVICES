package in.ssf.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "05-Provider-Service-API")
public interface ProviderClient {

    @GetMapping("/provider/{providerId}/user-id")
    Long getUserIdByProviderId(@PathVariable Long providerId);
    
}