package com.appsdeveloperblog.photoapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PhotoAppApiConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiConfigserverApplication.class, args);
	}

}
