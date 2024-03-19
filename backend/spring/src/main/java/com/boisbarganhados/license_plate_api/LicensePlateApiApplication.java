package com.boisbarganhados.license_plate_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LicensePlateApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensePlateApiApplication.class, args);
	}
	
	@GetMapping("/test")
	public static String teste(){
		return "License Plate API is running!";
	}

}
