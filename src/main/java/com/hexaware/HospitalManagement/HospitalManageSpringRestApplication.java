package com.hexaware.HospitalManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HospitalManageSpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManageSpringRestApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplateBuilder()
	        .interceptors((request, body, execution) -> {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	            if (authentication != null) {
	                Object credentials = authentication.getCredentials();
	                System.out.println("Credentials from SecurityContext: " + credentials);

	                if (credentials instanceof String token) {
	                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	                    System.out.println(" Added token to header: Bearer " + token);
	                } else {
	                    System.out.println(" Credentials not instance of String (actual: " + credentials.getClass() + ")");
	                }
	            } else {
	                System.out.println(" No authentication in SecurityContext");
	            }

	            return execution.execute(request, body);
	        })
	        .build();
	}



    private String getCurrentJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String token) {
            return token;
        }
        throw new RuntimeException("JWT token not available in security context");
    }
	
    
	

}
