package com.cultofcthulhu.projectallocation;

import com.cultofcthulhu.projectallocation.storage.StorageProperties;
import com.cultofcthulhu.projectallocation.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ProjectallocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectallocationApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
