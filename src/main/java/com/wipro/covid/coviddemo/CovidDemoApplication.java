package com.wipro.covid.coviddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CovidDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidDemoApplication.class, args);
	}

}
