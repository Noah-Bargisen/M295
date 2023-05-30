package com.ubs.m295_projectapplication.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectControllerTest {

    @Autowired
    ProjectController projectController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(2)
    public void testGetAllProjects() {
        assertThat(this.testRestTemplate.getForObject("/project", String.class)).contains("[").contains("]");
    }

    @Test
    @Order(2)
    public void testGetOneProject() {
        assertThat(this.testRestTemplate.getForObject("/project/1", String.class)).contains("projectId").contains("projectName");
    }

    @Test
    @Order(1)
    public void testPostProject() {
        assertThat(this.testRestTemplate.postForObject("/project", "{\"projectId\": 1, \"projectName\": \"test\"}", String.class).contains("projectId\":1"));
    }

    @Test
    @Order(3)
    public void testPutProject() {
        this.testRestTemplate.put("/project/1", "{\"projectId\": 1, \"projectName\": \"test\"}", String.class);
        assertThat(this.testRestTemplate.getForObject("/project/1", String.class)).contains("projectId").contains("projectName:test");
    }

    @Test
    @Order(4)
    public void testDeleteProject() {
        this.testRestTemplate.delete("/project/1", String.class);
        assertThat(this.testRestTemplate.getForObject("/project/1", String.class)).contains("projectId").contains("projectName");
    }
}