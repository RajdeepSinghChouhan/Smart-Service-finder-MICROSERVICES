package in.ssf.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Notification API Started");
	}

}
