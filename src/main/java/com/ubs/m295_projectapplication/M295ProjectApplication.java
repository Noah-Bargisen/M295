package com.ubs.m295_projectapplication;

import com.ubs.m295_projectapplication.configuration.SpringDAOConfiguration;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import com.ubs.module.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

@SpringBootApplication
@Slf4j
public class M295ProjectApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(M295ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Info");
        log.warn("Warn");
        log.error("Error");
        log.debug("Debug");
        log.trace("Trace");

    }
}
