package com.ubs.m295_projectapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.gen.controller.ProjectApi;
import com.ubs.gen.module.Project;
import com.ubs.gen.module.ProjectRequest;
import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ProjectController extends AbstractController implements ProjectApi {

    private final ProjectDao projectDao;

    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return ProjectApi.super.getObjectMapper();
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return ProjectApi.super.getRequest();
    }

    @Override
    public Optional<String> getAcceptHeader() {
        return ProjectApi.super.getAcceptHeader();
    }




    @Override
    public ResponseEntity<List<Project>> getProjects() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting projects");
            }
            log.info("Getting projects...");
            List<Project> projects = projectDao.getAllProjects();
            log.info("Projects retrieved...");
            return okRespond(projects);
        }  catch (SQLException exception) {
            log.warn("Error getting projects", exception);
            throwBadRequest("Error getting projects", exception);
        } catch (Exception exception) {
            log.error("Critical error getting projects", exception);
            throwInternalServerError("Critical error getting projects", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Integer> createProject(ProjectRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating project: {}", body);
            }
            log.info("Creating project...");
            int id = projectDao.addProject(body);
            log.info("Project created...");
            return okRespond(id);
        } catch (SQLException exception) {
            log.warn("Error creating project", exception);
            throwBadRequest("Error creating project", exception);
        } catch (Exception exception) {
            log.error("Critical error creating project", exception);
            throwInternalServerError("Critical error creating project", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Project> deleteProject(Integer projectId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Deleting project: {}", projectId);
            }
            log.info("Deleting project...");
            projectDao.deleteProjectById(projectId);
            log.info("Project deleted...");
            return okRespond(null);
        } catch (SQLException exception) {
            log.warn("Error deleting project", exception);
            throwBadRequest("Error deleting project", exception);
        } catch (Exception exception) {
            log.error("Critical error deleting project", exception);
            throwInternalServerError("Critical error deleting project", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Project> getProject(Integer projectId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting project: {}", projectId);
            }
            log.info("Getting project...");
            Project project = projectDao.getProjectById(projectId);
            log.info("Project retrieved...");
            return okRespond(project);
        } catch (SQLException exception) {
            log.warn("Error getting project", exception);
            throwBadRequest("Error getting project", exception);
        } catch (Exception exception) {
            log.error("Critical error getting project", exception);
            throwInternalServerError("Critical error getting project", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Project> updateProject(Integer projectId, ProjectRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Updating project: {}", projectId);
            }
            log.info("Updating project...");
            projectDao.updateProject(projectId, body);
            log.info("Project updated...");
            return okRespond(projectDao.getProjectById(projectId));
        } catch (SQLException exception) {
            log.warn("Error updating project", exception);
            throwBadRequest("Error updating project", exception);
        } catch (Exception exception) {
            log.error("Critical error updating project", exception);
            throwInternalServerError("Critical error updating project", exception);
        }
        return null;
    }
}
