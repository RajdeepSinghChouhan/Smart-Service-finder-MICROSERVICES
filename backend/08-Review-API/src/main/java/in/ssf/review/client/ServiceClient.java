package in.ssf.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.ssf.event.dto.ServiceDto;

@FeignClient("06-ALL-SERVICES-API")
public interface ServiceClient
{
    @GetMapping("/services/provider/{serviceId}")
    Long getProviderIdByServiceId(@PathVariable Long serviceId);
    
    @GetMapping("/services/data/{serviceId}")
    ServiceDto getServiceById(@PathVariable("serviceId") Long serviceId);
}