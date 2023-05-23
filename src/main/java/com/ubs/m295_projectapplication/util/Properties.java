package com.ubs.m295_projectapplication.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "m295")
@AllArgsConstructor
@Getter
public class Properties {

    private final String test;

}
