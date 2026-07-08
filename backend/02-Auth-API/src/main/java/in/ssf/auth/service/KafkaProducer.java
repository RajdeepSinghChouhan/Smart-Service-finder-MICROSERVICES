package in.ssf.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import in.ssf.event.dto.UserRegisteredEvent;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void publish(UserRegisteredEvent event){

        kafkaTemplate.send("user.registered", event);
    }

}