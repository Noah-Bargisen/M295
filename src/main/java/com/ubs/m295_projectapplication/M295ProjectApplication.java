package com.ubs.m295_projectapplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class M295ProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(M295ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Application started successfully!");

    }
}
