package com.mytel.vip_subscriber_management.caio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.mytel.vip_subscriber_management.database"})
@EntityScan(basePackages = {"com.mytel.vip_subscriber_management.database"})
@ComponentScan(basePackages = {"com.mytel.vip_subscriber_management"})
public class VipSubscriberManagementCAIO {

	public static void main(String[] args) {
		SpringApplication.run(VipSubscriberManagementCAIO.class, args);
	}

}
