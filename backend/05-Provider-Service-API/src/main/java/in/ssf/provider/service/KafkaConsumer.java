package in.ssf.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import in.ssf.event.dto.UserRegisteredEvent;
import in.ssf.provider.model.ProviderService;
import in.ssf.provider.repo.ProviderServiceRepo;

@Service
public class KafkaConsumer {

    @Autowired
    private ProviderServiceRepo repository;

    @KafkaListener(
            topics = "user.registered",
            groupId = "provider-service"
    )
    public void consume(UserRegisteredEvent event){
    	
    	  System.out.println("Received Event : " + event);
    	
	    	if (!"PROVIDER".equalsIgnoreCase(event.getRole())) {
	    		System.out.println(event.getRole());
	    		System.out.println("Skipped");
	            return;
	        }
	    	

        ProviderService profile = new ProviderService();

        profile.setUserId(event.getUserId());
        profile.setBusinessName("Not Updated");
        profile.setDescription("Not Updated");
        profile.setExperience("Not Updated");
        profile.setRating(0.0);
        profile.setVerified(false);
        

        repository.save(profile);

        System.out.println("Provider Profile Created");
    }

}