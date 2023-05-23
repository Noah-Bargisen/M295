package com.ubs.m295_projectapplication.controller;

import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.module.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/m295")
@Slf4j
public class ProjectController {

    private final static String GET_ALL_SOFTWARE_PATH = "/software";
    private final static String GET_ONE_SOFTWARE_PATH = "/software/{softwareId})";

    private final ProjectDao projectDao;

    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    @GetMapping(GET_ALL_SOFTWARE_PATH)
    public @ResponseBody List<Project> getAllSoftware() {
        log.info("Getting all software");
        return projectDao.getAllProjects();
    }

    @GetMapping(GET_ONE_SOFTWARE_PATH)
    public @ResponseBody Project getOneSoftware(@PathVariable("softwareId") int softwareId) {
        log.info("Getting one software");
        return projectDao.getProjectById(softwareId);
    }

}
