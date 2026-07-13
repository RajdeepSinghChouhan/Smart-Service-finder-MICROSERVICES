package in.ssf.allServices.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.allServices.client.ProviderServiceClient;
import in.ssf.allServices.dto.AllServiceDto;
import in.ssf.allServices.dto.AllServiceResponseDto;
import in.ssf.allServices.dto.ProviderServiceDto;
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
	
	public AllServiceResponseDto saveService(AllServiceDto dto,Long userId)
	{
		Long providerId = getProviderId(userId);
		
		dto.setProviderId(providerId);
		
		log.info("Saving Service Details for ProvderId {}",providerId);
		
		
		AllService entity = mapDtoToObject(dto);
		
		AllService save = serviceRepo.save(entity);
		
		AllServiceResponseDto dto1 = mapObjectToDto(save);
		
		return dto1;
	}
	
	
	public List<AllServiceResponseDto> getAllService()
	{
		log.info("Getting All Services");
		
		
		List<AllService> all = serviceRepo.findAll();
		
		List<AllServiceResponseDto> dtoList = new ArrayList<>();
		
		for(AllService service : all)
		{
			AllServiceResponseDto dto = mapObjectToDto(service);
			dtoList.add(dto);
		}
		
		if(dtoList.isEmpty())
		{
			throw new ServiceNotFoundException("Services Not Found");
		}
		
		return dtoList; 
	}
	
	public List<AllServiceResponseDto> getAllServiceOfProvider(Long userId)
	{
		Long providerId = getProviderId(userId);
		
		log.info("Getting All Services Of ProvderId {}",providerId);
		
		List<AllService> all = serviceRepo.findAllByProviderId(providerId);
		
		List<AllServiceResponseDto> dtoList = new ArrayList<>();
		
		for(AllService service : all)
		{
			AllServiceResponseDto dto = mapObjectToDto(service);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public AllServiceResponseDto getServiceById(Long serviceId)
	{
		AllService service = serviceRepo.findById(serviceId).orElseThrow(
				()-> new ServiceNotFoundException("Service Not Found for serviceId = "+serviceId));
		
		log.info("Getting Service by ServiceId {}",serviceId);
		
		return mapObjectToDto(service);
	}
	
	@Transactional
	public AllServiceResponseDto updateServiceById(Long serviceId,AllServiceDto request)
	{
		log.info("Update Service Details By ServiceID {}",serviceId);
		
		AllService object = mapDtoToObject(request);
		
		AllService service = serviceRepo.findById(serviceId).orElseThrow(
				()-> new ServiceNotFoundException("Service Not Found for serviceId = "+serviceId));
		
		if(object.getAvailable()!=null)
			service.setAvailable(object.getAvailable());
		
		if(object.getDescription()!=null)
			service.setDescription(object.getDescription());
		
		if(object.getPrice()!=null)
			service.setPrice(object.getPrice());
		
		if(object.getTitle()!=null)
			service.setTitle(object.getTitle());
		
		serviceRepo.save(service);
		
		AllServiceResponseDto dto = mapObjectToDto(object);
			
		return dto;
	}
	
	@Transactional
	public AllServiceResponseDto deleteServiceById(Long serviceId)
	{
		log.info("Deleting Service of serviceId {}",serviceId);
		
		AllService service = serviceRepo.findById(serviceId)
		        .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
		
		serviceRepo.delete(service);
		
		return mapObjectToDto(service);
		
	}
	
	public AllServiceResponseDto mapObjectToDto(AllService service)
	{
		ProviderServiceDto providerDto = providerServiceClient.getProviderDetailsById(service.getProviderId());
		
		AllServiceResponseDto dto = new AllServiceResponseDto();
		
		
		
		dto.setServiceId(service.getId());
		dto.setProviderId(service.getProviderId());
		dto.setProviderUserId(providerDto.getUserId());
		dto.setBusinessName(providerDto.getBusinessName());
		dto.setDescription(providerDto.getDescription());
		dto.setExperience(providerDto.getExperience());
		dto.setCategoryId(providerDto.getCategoryId());
		dto.setRating(providerDto.getRating());
		dto.setVerified(providerDto.getVerified());
		dto.setTitle(service.getTitle());
		dto.setPrice(service.getPrice());
		dto.setAvailable(service.getAvailable());
		dto.setCreatedAt(service.getCreatedAt());
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
