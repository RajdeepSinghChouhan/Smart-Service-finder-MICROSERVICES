package in.ssf.allServices.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.allServices.client.ProviderServiceClient;
import in.ssf.allServices.dto.AllServiceDto;
import in.ssf.allServices.exception.ProviderServiceUnavailableException;
import in.ssf.allServices.exception.ServiceNotFoundException;
import in.ssf.allServices.model.AllService;
import in.ssf.allServices.repo.AllServicesRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AllServiceService {

	@Autowired
	private AllServicesRepo serviceRepo;

	@Autowired
	private ProviderServiceClient providerServiceClient;
	
	public AllServiceDto saveService(AllServiceDto dto,Long userId)
	{
		Long providerId = getProviderId(userId);
		
		dto.setProviderId(providerId);
		
		log.info("Saving Service Details for ProvderId {}",providerId);
		
		
		AllService entity = mapDtoToObject(dto);
		
		AllService save = serviceRepo.save(entity);
		
		AllServiceDto dto1 = mapObjectToDto(save);
		
		return dto1;
	}
	
	
	public List<AllServiceDto> getAllService()
	{
		log.info("Getting All Services");
		
		
		List<AllService> all = serviceRepo.findAll();
		
		List<AllServiceDto> dtoList = new ArrayList<>();
		
		for(AllService service : all)
		{
			AllServiceDto dto = mapObjectToDto(service);
			dtoList.add(dto);
		}
		
		if(dtoList.isEmpty())
		{
			throw new ServiceNotFoundException("Services Not Found");
		}
		
		return dtoList; 
	}
	
	public List<AllServiceDto> getAllServiceOfProvider(Long userId)
	{
		Long providerId = getProviderId(userId);
		
		log.info("Getting All Services Of ProvderId {}",providerId);
		
		List<AllService> all = serviceRepo.findAllByProviderId(providerId);
		
		List<AllServiceDto> dtoList = new ArrayList<>();
		
		for(AllService service : all)
		{
			AllServiceDto dto = mapObjectToDto(service);
			dtoList.add(dto);
		}
		
		
		return dtoList;
	}
	
	public AllServiceDto getServiceById(Long serviceId)
	{
		AllService service = serviceRepo.findById(serviceId).orElseThrow(
				()-> new ServiceNotFoundException("Service Not Found for serviceId = "+serviceId));
		
		log.info("Getting Service by ServiceId {}",serviceId);
		
		return mapObjectToDto(service);
	}
	
	@Transactional
	public AllServiceDto updateServiceById(Long serviceId,AllServiceDto request)
	{
		log.info("Update Service Details By ServiceID {}",serviceId);
		
		AllService service = serviceRepo.findById(serviceId).orElseThrow(
				()-> new ServiceNotFoundException("Service Not Found for serviceId = "+serviceId));
		
		if(request.getAvailable()!=null)
			service.setAvailable(request.getAvailable());
		
		if(request.getDescription()!=null)
			service.setDescription(request.getDescription());
		
		if(request.getPrice()!=null)
			service.setPrice(request.getPrice());
		
		if(request.getTitle()!=null)
			service.setTitle(request.getTitle());
		
		serviceRepo.save(service);
			
		return request;
	}
	
	@Transactional
	public AllServiceDto deleteServiceById(Long serviceId)
	{
		log.info("Deleting Service of serviceId {}",serviceId);
		
		AllService service = serviceRepo.findById(serviceId)
		        .orElseThrow(() -> new ServiceNotFoundException("Service not found"));

		serviceRepo.delete(service);
		
		return mapObjectToDto(service);
		
	}
	
	public AllServiceDto mapObjectToDto(AllService service)
	{
		AllServiceDto dto = new AllServiceDto();
		dto.setId(service.getId());
		dto.setAvailable(service.getAvailable());
		dto.setCategoryId(service.getCategoryId());
		dto.setCreatedAt(service.getCreatedAt());
		dto.setDescription(service.getDescription());
		dto.setPrice(service.getPrice());
		dto.setProviderId(service.getProviderId());
		dto.setTitle(service.getTitle());
		return dto;
	}
	
	@CircuitBreaker(name = "providerService", fallbackMethod = "providerFallback")
	Long getProviderId(Long userId)
	{
		return providerServiceClient.getProviderIdByUserId(userId);
	}
	
	public Long providerFallback(Long userId, Exception ex) {
		 throw new ProviderServiceUnavailableException(
		            "Provider Service is currently unavailable. Please try again later.");
	}
	
	public AllService mapDtoToObject(AllServiceDto dto)
	{
		AllService entity = new AllService();
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setCategoryId(dto.getCategoryId());
		entity.setAvailable(dto.getAvailable());
		entity.setCreatedAt(dto.getCreatedAt());
		entity.setProviderId(dto.getProviderId());
		return entity;
	}
}
