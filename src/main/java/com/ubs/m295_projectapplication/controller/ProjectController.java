package com.ubs.m295_projectapplication.controller;

import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.module.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/m295")
@Slf4j
public class ProjectController {

    private final static String GET_ALL_PROJECT_PATH = "/project";
    private final static String GET_ONE_PROJECT_PATH = "/project/{projectId}";

    private final static String ADMIN_POST_PROJECT_PATH = "/admin/project";
    private final static String ADMIN_PUT_PROJECT_PATH = "/admin/project}";
    private final static String ADMIN_DELETE_PROJECT_PATH = "/admin/project/{projectId}";

    private final ProjectDao projectDao;

    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    @GetMapping(GET_ALL_PROJECT_PATH)
    public @ResponseBody ResponseEntity getAllProjects() {
        log.info("Getting all projects");
        try {
            List<Project> projectList = projectDao.getAllProjects();
            return ResponseEntity.ok().body(projectList);
        } catch (SQLException e) {
            log.warn("Error getting projects", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(GET_ONE_PROJECT_PATH)
    public @ResponseBody ResponseEntity getOneSoftware(@PathVariable("projectId") int projectId) {
        log.info("Getting one project");
        try {
            Project project = projectDao.getProjectById(projectId);
            return ResponseEntity.ok().body(project);
        } catch (SQLException e) {
            log.warn("Error getting project", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(ADMIN_POST_PROJECT_PATH)
    public ResponseEntity postSoftware(@RequestBody Project project) {
        log.info("Posting one project");
        try {
            projectDao.addProject(project);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Error posting project", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(ADMIN_PUT_PROJECT_PATH)
    public ResponseEntity putSoftware(@RequestBody Project project) {
        log.info("Putting one project");
        try {
            projectDao.updateProject(project);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Error putting project", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(ADMIN_DELETE_PROJECT_PATH)
    public ResponseEntity deleteSoftware(@PathVariable("projectId") int projectId) {
        log.info("Deleting one project");
        try {
            projectDao.deleteTeamById(projectId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Error deleting project", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
