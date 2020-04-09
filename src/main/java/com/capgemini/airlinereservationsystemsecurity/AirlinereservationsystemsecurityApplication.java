package com.capgemini.airlinereservationsystemsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AirlinereservationsystemsecurityApplication extends AbstractSecurityWebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AirlinereservationsystemsecurityApplication.class, args);
	}
	
	 @Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }

}
