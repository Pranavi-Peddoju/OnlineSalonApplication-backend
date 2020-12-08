package com.cg;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineSalonApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineSalonApplication.class, args);
	}
	
	@Bean(name = "random")
	public Random getRandom() {
		return new Random();
	}

}
