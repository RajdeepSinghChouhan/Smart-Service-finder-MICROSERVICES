package in.ssf.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Eureka Server Started");
	
	}

}
