package in.ssf.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.provider.Application;
import in.ssf.provider.dto.ProviderServiceDto;
import in.ssf.provider.exception.ProviderAlreadyExist;
import in.ssf.provider.exception.ProviderNotFound;
import in.ssf.provider.model.ProviderService;
import in.ssf.provider.repo.ProviderServiceRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProviderServiceService {

    private final Application application;
	
	@Autowired
	private ProviderServiceRepo providerRepo;

    ProviderServiceService(Application application) {
        this.application = application;
    }

	//register Provider
	public ProviderServiceDto saveProvider(ProviderService dto)
	{
		if(providerRepo.findByUserId(dto.getUserId()).isPresent()) {
		    throw new ProviderAlreadyExist("Provider already exists");
		}
		
		log.info("Saving Provider Details for UserId {} ",dto.getUserId());
		
		
		ProviderService save = providerRepo.save(dto);
		
		return mapObjectToDto(save);
		
	}
	
	
	//Send Provider Data
	public ProviderServiceDto getProvider(Long userId)
	{
		
		log.info("Getting Provider Details for UserId {} ", userId);
		
		ProviderService provider = providerRepo.findByUserId(userId)
									.orElseThrow(
											()->
											new ProviderNotFound("Provider not found with userId: " + userId)
											);
		return mapObjectToDto(provider);
		
	}
	
	//Update Provider
	@Transactional
	public ProviderServiceDto updateProvider(ProviderService dto,Long userId)
	{
		
		log.info("Updating Provider Details for UserId {} ",userId);
		
		
		ProviderService provider = providerRepo.findByUserId(userId)
				.orElseThrow(
						()->
						new ProviderNotFound("Provider not found with userId: " + userId)
						);
		
		if(dto.getBusinessName()!=null)
			provider.setBusinessName(dto.getBusinessName());
		if(dto.getDescription()!=null)
			provider.setDescription(dto.getDescription());
		if(dto.getExperience()!=null)
			provider.setExperience(dto.getExperience());
		if(dto.getRating()!=null)
			provider.setRating(dto.getRating());
		if(dto.getVerified()!=null)
			provider.setVerified(dto.getVerified());
		
		ProviderService save = providerRepo.save(provider);
		
		ProviderServiceDto providerServiceDto = new ProviderServiceDto();
		
		if(save.getVerified()!=null)
			providerServiceDto.setVerified(save.getVerified());
		if(save.getBusinessName()!=null)
			providerServiceDto.setBusinessName(save.getBusinessName());
		if(save.getDescription()!=null)
			providerServiceDto.setDescription(save.getDescription());
		if(save.getExperience()!=null)
			providerServiceDto.setExperience(save.getExperience());
		if(save.getRating()!=null)
			providerServiceDto.setRating(save.getRating());
		
		return providerServiceDto;
	}
	
	//delete Provider
	@Transactional
	public ProviderServiceDto deleteProvider(Long userId)
	{
		log.info("Deleting Provider Details for UserId {}",userId);
		
		ProviderService provider = providerRepo.findByUserId(userId)
				.orElseThrow(
						()->
						new ProviderNotFound("Provider not found with userId: " + userId)
						);
		providerRepo.delete(provider);
		
		return mapObjectToDto(provider);
		
	}
	
	//Send Provider Data
	public Long getProviderId(Long userId)
	{
		log.info("Getting Provider Id By UserId {}",userId);
		ProviderService provider = providerRepo.findByUserId(userId)
									.orElseThrow(
											()->
											new ProviderNotFound("Provider not found with userId: " + userId)
											);
		return provider.getId();
	}

	public ProviderServiceDto mapObjectToDto(ProviderService dto)
	{
		ProviderServiceDto providerServiceDto = new ProviderServiceDto();
		
		ProviderService save = providerRepo.save(dto);
		providerServiceDto.setBusinessName(save.getBusinessName());
		providerServiceDto.setCategoryId(save.getCategoryId());
		providerServiceDto.setDescription(save.getDescription());
		providerServiceDto.setExperience(save.getExperience());
		providerServiceDto.setRating(save.getRating());
		providerServiceDto.setUserId(save.getUserId());
		providerServiceDto.setVerified(save.getVerified());
		
		return providerServiceDto;
	}
}
