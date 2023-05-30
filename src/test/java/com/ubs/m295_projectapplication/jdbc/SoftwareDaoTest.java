package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Project;
import com.ubs.gen.module.Software;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamMember;
import com.ubs.m295_projectapplication.service.extractor.SoftwareSetExtractor;
import com.ubs.m295_projectapplication.service.extractor.TeamMemberSetExtractor;
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

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class SoftwareDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    private SoftwareDao softwareDao;
    @BeforeEach
    void setUp() {
        this.softwareDao = new SoftwareDao(this.namedParameterJdbcTemplate);
    }

    @Test
    void getAllProjects() throws Exception {
        this.softwareDao.getAllSoftware();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId")
                , any(SoftwareSetExtractor.class));
    }

    @Test
    void getTeamMemberById() throws Exception {
        Software software = new Software();
        software.setSoftwareId("GGG");
        software.setSoftwareName("Project1");
        software.setStatus(Software.StatusEnum.TESTING);
        software.setSoftwareVersion("1.0");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(SoftwareSetExtractor.class)))
                .thenReturn(List.of(software));
        this.softwareDao.getSoftwareById("GGG");
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE softwareId = :softwareId")
                ,argumentCaptor.capture(), any(SoftwareSetExtractor.class));

        assertEquals("GGG", argumentCaptor.getValue().getValue("softwareId"));
    }

    @Test
    void addTeamMember() throws Exception {
        Software software = new Software().softwareId("GGG").softwareName("Project1").status(Software.StatusEnum.TESTING).softwareVersion("1.0").project(new Project().projectId(1)).team(new Team().teamId(1));
        this.softwareDao.addSoftware(software);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq
                        ("insert into software (softwareId, team, project, status) values (:softwareId, :team, :project, :status)")
                , argumentCaptor.capture());
        assertEquals(software.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
        assertEquals(software.getTeam().getTeamId(), argumentCaptor.getValue().getValue("team"));
        assertEquals(software.getProject().getProjectId(), argumentCaptor.getValue().getValue("project"));
        assertEquals(software.getStatus().toString(), argumentCaptor.getValue().getValue("status"));
    }

    @Test
    void updateProject() throws Exception {
        Software software = new Software().softwareId("GGG").softwareName("Project1").status(Software.StatusEnum.TESTING).softwareVersion("1.0").project(new Project().projectId(1)).team(new Team().teamId(1));

        this.softwareDao.updateSoftware(software);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update software set softwareName = :softwareName, softwareVersion = :softwareVersion, team = :teamId, project = :projectId, status = :status where softwareId = :softwareId")
                , argumentCaptor.capture());

        assertEquals(software.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
        assertEquals(software.getSoftwareName(), argumentCaptor.getValue().getValue("softwareName"));
        assertEquals(software.getSoftwareVersion(), argumentCaptor.getValue().getValue("softwareVersion"));
        assertEquals(software.getTeam().getTeamId(), argumentCaptor.getValue().getValue("teamId"));
        assertEquals(software.getProject().getProjectId(), argumentCaptor.getValue().getValue("projectId"));
        assertEquals(software.getStatus().toString(), argumentCaptor.getValue().getValue("status"));
    }

    @Test
    void deleteProjectById() throws Exception {
        this.softwareDao.deleteSoftwareById("GGG");
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from Software where softwareId = :softwareId")
                , argumentCaptor.capture());
        assertEquals("GGG", argumentCaptor.getValue().getValue("softwareId"));
    }
}
