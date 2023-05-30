package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.gen.module.Project;
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

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
                ("select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team")
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
        verify(this.namedParameterJdbcTemplate).query(eq("select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team where projectId = :projectId")
                , argumentCaptor.capture(), any(ProjectSetExtractor.class));
        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
    }

    @Test
    void addProject() throws Exception {
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Project1");
        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.projectDao.addProject(project);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("insert into project (projectName) values (:projectName)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals("Project1", argumentCaptor.getValue().getValue("projectName"));
    }

    @Test
    void updateProject() throws Exception {
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Project1");
        this.projectDao.updateProject(project);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update project set projectName = :projectName where projectId = :projectId")
                , argumentCaptor.capture());

        assertEquals(project.getProjectId(), argumentCaptor.getValue().getValue("projectId"));
        assertEquals(project.getProjectName(), argumentCaptor.getValue().getValue("projectName"));
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