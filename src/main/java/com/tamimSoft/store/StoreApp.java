package com.tamimSoft.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class StoreApp {

    public static void main(String[] args) {
        SpringApplication.run(StoreApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }
}
