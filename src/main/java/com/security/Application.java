package com.security;

import java.io.Closeable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import com.security.entity.Role;
import com.security.entity.User;
import com.security.repository.UserRepository;

import io.jsonwebtoken.io.IOException;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application implements CommandLineRunner, Closeable {

	@Autowired
	private UserRepository repository;

	public static ConfigurableApplicationContext run;
	
	@Value("${adminEmail}")
	private String adminEmail;
	
	@Value("${adminFirstname}")
	private String adminFirstname;
	
	@Value("${adminLastname}")
	private String adminLastname;
	
	@Value("${adminPassword}")
	private String adminPassword;

	public static void main(String[] args) {
		run = SpringApplication.run(Application.class, args);
	}

	@Override
	public void close() throws IOException {
		run.close();
	}

	@Override
	public void run(String... args) throws Exception {
		User admin = repository.findByRole(Role.ADMIN);
		if (null == admin) {
			User user = new User();

//			user.setId(1);
			user.setEmail(adminEmail);
			user.setFirstname(adminFirstname);
			user.setLastname(adminLastname);
			user.setRole(Role.ADMIN);
			user.setPassword(adminPassword);

			repository.save(user);
		}

	}

}
