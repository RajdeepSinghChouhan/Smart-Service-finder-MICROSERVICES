package in.ssf.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Review API Started");
	}
}
