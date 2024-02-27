package com.self.tms;

import com.self.tms.models.Theatre;
import com.self.tms.services.MovieService;
import com.self.tms.services.ShowService;
import com.self.tms.services.TheatreServices;
import com.self.tms.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.self.tms")
public class TicketManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketManagementSystemApplication.class, args);
	}

	/*
	* Movie, User,Theatre and screen will be initialised via service constructor
	* Movie in theatres will be added via api
	* Show will be added via api
	*
	* */

}
