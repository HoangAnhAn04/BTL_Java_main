package com.rbai.btl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BtlServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtlServerApplication.class, args);
	}

}
