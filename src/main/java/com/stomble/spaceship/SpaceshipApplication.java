/*
Author: Yazad Davur <yazadjd@yahoo.com>
Purpose: Manage Stomble's fleet of Spaceships

We develop a REST API to manage the logistics of Stomble’s fleet of spaceships.
The API will store information about the different locations as well as the
spaceships stationed at those locations. We create a Controller that contains
the URI endpoints with the help of SpringBoot and test the functionality
using Swagger.

Assumptions:
- Travel to any location happens in a single trip.
- All spaceships are stationed at some existent location.
- Travel happens instantaneously.

 */



package com.stomble.spaceship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpaceshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.stomble.spaceship")).build();
	}

}
