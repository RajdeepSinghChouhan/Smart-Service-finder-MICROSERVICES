package in.ssf.allServices.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.allServices.dto.AllServiceDto;
import in.ssf.allServices.dto.AllServiceResponseDto;
import in.ssf.allServices.model.AllService;
import in.ssf.allServices.service.AllServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/services")
@RefreshScope
@Tag(name = "AllServices Controller", description = "AllServices Related APIs")
public class AllServiceRestController {

	@Autowired
	private AllServiceService allservice;
	
	
	@PostMapping("/data")
	@Operation(summary = "Save Services Data")
	public AllServiceResponseDto saveServiceData(@Valid @RequestBody AllServiceDto service,@RequestHeader("X-User-Id") Long userId)
	{
		return allservice.saveService(service,userId);
	}
	
	@GetMapping("/data")
	@Operation(summary = "Get All Services")
	public List<AllServiceResponseDto> getAllService()
	{
		return allservice.getAllService();	
	}
	
	@GetMapping("/data/my-services")
	@Operation(summary = "Get Services Of Provider")
	public List<AllServiceResponseDto> getAllServiceOfProvider(@RequestHeader("X-User-Id") Long userId)
	{
		return allservice.getAllServiceOfProvider(userId);
	}
	
	@GetMapping("/data/{serviceId}")
	@Operation(summary = "Get Service By ServiceId")
	public AllServiceResponseDto getServiceById(@PathVariable Long serviceId)
	{
		return allservice.getServiceById(serviceId);
	}
	
	@PutMapping("/data/{serviceId}")
	@Operation(summary = "Update Service By ServiceId")
	public AllServiceResponseDto updateServiceById(@PathVariable Long serviceId, @Valid @RequestBody AllServiceDto service)
	{
		return allservice.updateServiceById(serviceId,service);
	}
	
	@DeleteMapping("/data/{serviceId}")
	@Operation(summary = "Delete Service Data By ServiceId")
	public AllServiceResponseDto deleteServiceById(@PathVariable Long serviceId)
	{
		return allservice.deleteServiceById(serviceId);
	}
	
	@GetMapping("/provider/{serviceId}")
	@Operation(summary = "Get ProviderId By ServiceId")
	public Long getProviderIdByServiceId(@PathVariable Long serviceId)
	{
		AllServiceResponseDto serviceById = allservice.getServiceById(serviceId);

	    return serviceById.getProviderId();
	}
}
