package com.example.demo;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {
	@Value("${spring.application.name}")
	private String appName;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner inspectorBean(ApplicationContext appCtx) {
		return args -> {
			System.out.printf("inspecting beans provided by Spring Boot in %s.......", appName).println();

			String[] beans = appCtx.getBeanDefinitionNames();
			Arrays.sort(beans);
			for(String bean: beans)
			{
				System.out.println(bean);
			}
			System.out.println("Ending the inspection!");
		};
	}
}
