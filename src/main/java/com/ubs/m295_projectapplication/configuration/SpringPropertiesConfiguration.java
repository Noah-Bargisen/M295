package com.ubs.m295_projectapplication.configuration;

import com.ubs.m295_projectapplication.util.Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(Properties.class)
@Configuration
public class SpringPropertiesConfiguration {

}
