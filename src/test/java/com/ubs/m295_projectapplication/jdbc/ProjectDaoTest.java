package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.module.Project;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ProjectDao projectDao;
    @BeforeEach
    void setUp() {
        this.projectDao = new ProjectDao(this.namedParameterJdbcTemplate);
    }

    @Test
    void getAllProjects() {
        this.projectDao.getAllProjects();
        verify(this.namedParameterJdbcTemplate).query(eq
                ("select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team")
                , any(ProjectSetExtractor.class));

    }

    @Test
    void getProjectById() {
        Project project = new Project();
        project.setProjectId(1L);
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
    void addProject() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Project1");
        this.projectDao.addProject(project);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("insert into project (projectName) values (:projectName)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
        assertEquals("Project1", argumentCaptor.getValue().getValue("projectName"));
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteTeamById() {
    }
}