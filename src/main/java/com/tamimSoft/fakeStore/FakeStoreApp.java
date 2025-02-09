package com.tamimSoft.fakeStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class FakeStoreApp {

    public static void main(String[] args) {
        SpringApplication.run(FakeStoreApp.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }
}
