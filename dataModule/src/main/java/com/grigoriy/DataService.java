package com.grigoriy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories("com.grigoriy.*")
@EntityScan("com.grigoriy.*")
@ComponentScan("com.grigoriy.*")
@EnableScheduling //нужна чтобы каждые n секунд дергать рэдис и спрашивать ЕСТЬ ЧЕ?
@SpringBootApplication
public class DataService
{
    public static void main(String[] args) {
        SpringApplication.run(DataService.class);
    }
}
