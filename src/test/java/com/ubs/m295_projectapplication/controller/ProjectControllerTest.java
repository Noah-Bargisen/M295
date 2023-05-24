package com.ubs.m295_projectapplication.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {

    @Autowired
    ProjectController projectController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testGetAllProjects() {
        assertThat(this.testRestTemplate.getForObject("/m295/project", String.class)).contains("[").contains("]");
    }
}