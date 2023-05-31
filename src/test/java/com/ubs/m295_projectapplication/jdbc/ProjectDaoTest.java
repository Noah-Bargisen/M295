package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Project;
import com.ubs.gen.module.ProjectRequest;
import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private GeneratedKeyHolder generatedKeyHolder;

    private ProjectDao projectDao;
    @BeforeEach
    void setUp() {
        this.projectDao = new ProjectDao(this.namedParameterJdbcTemplate, generatedKeyHolder);
    }

    @Test
    void getAllProjects() throws Exception {
        this.projectDao.getAllProjects();
        verify(this.namedParameterJdbcTemplate).query(eq
                ("select * from project p")
                , any(ProjectSetExtractor.class));
    }

    @Test
    void getProjectById() throws Exception {
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Project1");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(ProjectSetExtractor.class)))
                .thenReturn(List.of(project));
        this.projectDao.getProjectById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq("select * from project p where projectId = :projectId")
                , argumentCaptor.capture(), any(ProjectSetExtractor.class));
        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
    }

    @Test
    void addProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName("Project1");
        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.projectDao.addProject(projectRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("insert into project (projectName) values (:projectName)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(projectRequest.getProjectName(), argumentCaptor.getValue().getValue("projectName"));
    }


    @Test
    void addNullFieldProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName(null);
        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.projectDao.addProject(projectRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("insert into project (projectName) values (:projectName)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(projectRequest.getProjectName(), argumentCaptor.getValue().getValue("projectName"));
    }

    @Test
    void addNullProject() {
        ProjectRequest projectRequest = null;
        assertThrows(Exception.class, () -> this.projectDao.addProject(projectRequest));
    }

    @Test
    void updateProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName("Project1");
        this.projectDao.updateProject(1, projectRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update project set projectName = :projectName where projectId = :projectId")
                , argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
        assertEquals(projectRequest.getProjectName(), argumentCaptor.getValue().getValue("projectName"));
    }

    @Test
    void updateNullFieldProject() throws Exception {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProjectName(null);
        this.projectDao.updateProject(1, projectRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update project set projectName = :projectName where projectId = :projectId")
                , argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
        assertEquals(projectRequest.getProjectName(), argumentCaptor.getValue().getValue("projectName"));
    }


    @Test
    void updateNullProject() {
        ProjectRequest projectRequest = null;
        assertThrows(Exception.class, () -> this.projectDao.updateProject(1, projectRequest));
    }

    @Test
    void deleteProjectById() throws Exception {
        this.projectDao.deleteProjectById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from project where projectId = :projectId")
                , argumentCaptor.capture());
        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
    }
}