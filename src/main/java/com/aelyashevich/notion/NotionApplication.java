package com.aelyashevich.notion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableMongoAuditing
@SpringBootApplication
public class NotionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotionApplication.class, args);
    }

}
