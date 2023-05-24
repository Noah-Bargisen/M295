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

    @Test
    public void testGetOneProject() {
        assertThat(this.testRestTemplate.getForObject("/m295/project/1", String.class)).contains("projectId").contains("projectName");
    }

    @Test
    public void testPostProject() {
        assertThat(this.testRestTemplate.postForObject("/m295/admin/project", "{\"projectId\": 1, \"projectName\": \"test\"}", String.class).contains("1"));
    }

    @Test
    public void testPutProject() {
        assertThat(this.testRestTemplate.postForObject("/m295/admin/project", "{\"projectId\": 1, \"projectName\": \"test\"}", String.class).contains("1"));
    }

    @Test
    public void testDeleteProject() {
        //assertThat(this.testRestTemplate.delete("/m295/admin/project", "{\"projectId\": 1, \"projectName\": \"test\"}", String.class);).contains("1");
    }
}