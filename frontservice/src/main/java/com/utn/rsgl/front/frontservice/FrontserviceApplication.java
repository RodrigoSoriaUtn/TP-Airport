package com.utn.rsgl.front.frontservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication public class FrontserviceApplication {


	public static void main(String[] args) {
		SpringApplication.run(FrontserviceApplication.class, args);
	}
}
