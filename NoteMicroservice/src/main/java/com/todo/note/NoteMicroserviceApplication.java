package com.todo.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*************************************************************************************************************
 *
 * purpose:Note microservice application
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "NoteMicroservice")
@EnableFeignClients
public class NoteMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteMicroserviceApplication.class, args);
	}

}
