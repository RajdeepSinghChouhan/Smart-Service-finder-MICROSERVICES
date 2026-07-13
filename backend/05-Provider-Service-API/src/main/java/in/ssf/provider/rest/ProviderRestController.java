package in.ssf.provider.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.provider.dto.ProviderServiceDto;
import in.ssf.provider.model.ProviderService;
import in.ssf.provider.service.ProviderServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/provider")
@Tag(name = "ProviderService Controller", description = "ProviderService Related APIs")
public class ProviderRestController {

	@Autowired
	private ProviderServiceService service;
	
	@PostMapping("/data")
	@Operation(summary = "Save Provider Data")
	public ProviderServiceDto saveProviderDetails(@Valid @RequestBody ProviderService request,@RequestHeader("X-User-Id") Long userId)
	{
		request.setUserId(userId);
		return service.saveProvider(request);
	}
	
	@GetMapping("/data")
	@Operation(summary = "Get Provider Data")
	public ProviderServiceDto getProviderDetails(@RequestHeader("X-User-Id") Long userId)
	{
		return service.getProvider(userId);	
	}
	
	@PutMapping("/data")
	@Operation(summary = "Update Provider Data")
	public ProviderServiceDto updateProviderDetails(@Valid @RequestBody ProviderService request,
								@RequestHeader("X-User-Id") Long  userId)
	{
		return service.updateProvider(request,userId);
	}
	
	@DeleteMapping("/data")
	@Operation(summary = "Delete Provider Data")
	public ProviderServiceDto deleteProvider(@RequestHeader("X-User-Id") Long userId)
	{
		return service.deleteProvider(userId);
	}
	
	@GetMapping("/getId")
	@Operation(summary = "Get ProviderId By UserID")
	public Long getProviderIdByUserId(@RequestHeader("X-User-Id") Long userId)
	{
		return service.getProviderId(userId);	
	}
	
	@GetMapping("/data/{id}")
	@Operation(summary = "Get Provider Data By Provider Id")
	public ProviderServiceDto getProviderDetailsById(@PathVariable Long  id)
	{
		return service.getProviderById(id);
	}
	
	@GetMapping("/{providerId}/user-id")
	public Long getUserIdByProviderId(@PathVariable Long providerId)
	{
		return service.getUserId(providerId);
	}
	
	@GetMapping("/all")
	public List<ProviderServiceDto> getAllProviders() {
	    return service.getAllProviders();
	}
}
