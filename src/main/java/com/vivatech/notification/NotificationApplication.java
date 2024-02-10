package com.vivatech.notification;

import com.vivatech.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vivatech.notification", "com.vivatech.entities"})
@EntityScan(basePackages = {"com.vivatech.notification.notificationmodel", "com.vivatech.model.ums"})
@EnableJpaRepositories({"com.vivatech.notification.repositories", "com.vivatech.repository"})
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
