package com.chakravyuha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Chakravyuha Employee Management System.
 * This application provides CRUD operations for employee personal and department information.
 * 
 * @author Chakravyuha Team
 * @version 1.0
 */
@SpringBootApplication
public class EmployeeApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
