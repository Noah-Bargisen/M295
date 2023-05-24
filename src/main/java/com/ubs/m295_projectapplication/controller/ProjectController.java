package com.ubs.m295_projectapplication.controller;

import com.ubs.gen.controller.ProjectApi;
import com.ubs.gen.module.ProjectAdminBody;
import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.gen.module.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/m295")
@Slf4j
public class ProjectController extends AbstractController implements ProjectApi{
    private final ProjectDao projectDao;

    public ProjectController(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    @Override
    public ResponseEntity<List<Project>> getProjects() {
        log.info("Getting all projects");
        try {
            List<Project> projectList = projectDao.getAllProjects();
            return okRespond(projectList);
        } catch (SQLException e) {
            log.warn("Error getting projects", e);

            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error getting projects", e);
            throw internalServerErrorRespond(e);

        }
    }

    @Override
    public ResponseEntity<Project> getProject(Long projectId) {
        log.info("Getting one project");
        try {
            Project project = projectDao.getProjectById(Math.toIntExact(projectId));
            return okRespond(project);
        } catch (SQLException e) {
            log.warn("Error getting project", e);
            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error getting project", e);
            throw internalServerErrorRespond(e);
        }
    }

    @Override
    public ResponseEntity<Project> postProject(ProjectAdminBody projectAdminBody) {
        log.info("Posting one project");
        try {
            Project project = new Project().projectName(projectAdminBody.getProjectName());
            int status = projectDao.addProject(project);
            return okRespond(project);
        } catch (SQLException e) {
            log.warn("Error posting project", e);
            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error posting project", e);
            throw internalServerErrorRespond(e);
        }
    }

    @Override
    public ResponseEntity<Project> putProject(Project project) {
        log.info("Putting one project");
        try {
            int status = projectDao.updateProject(project);
            return okRespond(project);
        } catch (SQLException e) {
            log.warn("Error putting project", e);
            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error putting project", e);
            throw internalServerErrorRespond(e);
        }
    }

    @Override
    public ResponseEntity<Integer> deleteProject(Long projectId) {
        log.info("Deleting one project");
        try {
            int status = projectDao.deleteTeamById(Math.toIntExact(projectId));
            return okRespond(status);
        } catch (SQLException e) {
            log.warn("Error deleting project", e);
            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error deleting project", e);
            throw internalServerErrorRespond(e);
        }
    }

}
