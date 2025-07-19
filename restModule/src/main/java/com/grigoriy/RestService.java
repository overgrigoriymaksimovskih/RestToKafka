package com.grigoriy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan("com.grigoriy.*")
@ComponentScan("com.grigoriy.*")
@SpringBootApplication
public class RestService
{
    public static void main(String[] args) {
        // Запуск Spring Boot
        SpringApplication.run(RestService.class);
    }
}