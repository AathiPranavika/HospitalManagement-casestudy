package com.hexaware.microService.hospitalMangement.Message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MicroServiceMessageHospitalMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceMessageHospitalMangementApplication.class, args);
	}

	@Bean
	public RestTemplate  getRestTemplate() {
		
		return new RestTemplate();
		
	}

}
