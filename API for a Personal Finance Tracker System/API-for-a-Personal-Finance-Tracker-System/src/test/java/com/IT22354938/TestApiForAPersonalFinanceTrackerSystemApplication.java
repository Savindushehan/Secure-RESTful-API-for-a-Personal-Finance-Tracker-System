package com.IT22354938;

import org.springframework.boot.SpringApplication;

public class TestApiForAPersonalFinanceTrackerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(ApiForAPersonalFinanceTrackerSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
