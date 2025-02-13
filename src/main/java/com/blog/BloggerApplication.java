package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication //project starting points
public class BloggerApplication { //this also config class

	public static void main(String[] args) {
		SpringApplication.run(BloggerApplication.class, args);
	}


}
