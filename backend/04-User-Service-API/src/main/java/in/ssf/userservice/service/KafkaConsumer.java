package in.ssf.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ssf.event.dto.UserRegisteredEvent;
import in.ssf.userservice.model.UserService;
import in.ssf.userservice.repo.UserServiceRepo;

@Service
public class KafkaConsumer {

    @Autowired
    private UserServiceRepo repository;

    @Transactional
    @KafkaListener(
            topics = "user.registered",
            groupId = "user-service"
    )
    public void consume(UserRegisteredEvent event){

        UserService profile = new UserService();

        
        profile.setUsername(event.getUsername());
        profile.setFullName(event.getUsername());
        profile.setEmail(event.getUsername()+"@gmail.com");
        profile.setAddress("Not Updated");
        profile.setPhone("0000000000");
        profile.setProfileImage(null);

        repository.save(profile);

        System.out.println("Profile Created");
    }

}