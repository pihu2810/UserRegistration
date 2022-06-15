package com.Bridgelabz.UserRegistration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class UserRegistrationApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserRegistrationApplication.class, args);
		System.out.println("Welcome to the UserRegistration");
	}

}
