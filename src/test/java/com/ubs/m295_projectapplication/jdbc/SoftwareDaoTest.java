package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Software;
import com.ubs.gen.module.SoftwareRequest;
import com.ubs.m295_projectapplication.service.extractor.SoftwareSetExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
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
    void getAllSoftwares() throws Exception {
        this.softwareDao.getAllSoftware();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId")
                , any(SoftwareSetExtractor.class));
    }

    @Test
    void getSoftwareById() throws Exception {
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
    void getSoftwareByProjectId() throws Exception {
        Software software = new Software();
        software.setSoftwareId("GGG");
        software.setSoftwareName("Project1");
        software.setStatus(Software.StatusEnum.TESTING);
        software.setSoftwareVersion("1.0");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(SoftwareSetExtractor.class)))
                .thenReturn(List.of(software));
        this.softwareDao.getAllSoftwareByProjectId(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE project = :project")
                ,argumentCaptor.capture(), any(SoftwareSetExtractor.class));

        assertEquals(1, argumentCaptor.getValue().getValue("project"));
    }




    @Test
    void getSoftwareBy0ProjectId() throws Exception {
        Software software = new Software();
        software.setSoftwareId("GGG");
        software.setSoftwareName("Project1");
        software.setStatus(Software.StatusEnum.TESTING);
        software.setSoftwareVersion("1.0");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(SoftwareSetExtractor.class)))
                .thenReturn(List.of(software));
        this.softwareDao.getAllSoftwareByProjectId(0);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE project = :project")
                ,argumentCaptor.capture(), any(SoftwareSetExtractor.class));

        assertEquals(0, argumentCaptor.getValue().getValue("project"));
    }

    @Test
    void getSoftwareByNullId() throws Exception {
        Software software = new Software();
        software.setSoftwareId(null);
        software.setSoftwareName("Project1");
        software.setStatus(Software.StatusEnum.TESTING);
        software.setSoftwareVersion("1.0");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(SoftwareSetExtractor.class)))
                .thenReturn(List.of(software));
        this.softwareDao.getSoftwareById(software.getSoftwareId());
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE softwareId = :softwareId")
                ,argumentCaptor.capture(), any(SoftwareSetExtractor.class));

        assertEquals(software.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
    }



    @Test
    void addSoftware() throws Exception {
        SoftwareRequest softwareRequest = new SoftwareRequest().softwareId("GGG").softwareName("Project1").status(SoftwareRequest.StatusEnum.TESTING).softwareVersion("1.0").projectId(1).teamId(1);
        this.softwareDao.addSoftware(softwareRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq
                        ("insert into software (softwareId, softwareName, softwareVersion, team, project, status) values (:softwareId, :softwareName, :softwareVersion, :team, :project, :status)")
                , argumentCaptor.capture());
        assertEquals(softwareRequest.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
        assertEquals(softwareRequest.getSoftwareName(), argumentCaptor.getValue().getValue("softwareName"));
        assertEquals(softwareRequest.getSoftwareVersion(), argumentCaptor.getValue().getValue("softwareVersion"));
        assertEquals(softwareRequest.getTeamId(), argumentCaptor.getValue().getValue("team"));
        assertEquals(softwareRequest.getProjectId(), argumentCaptor.getValue().getValue("project"));
        assertEquals(softwareRequest.getStatus().toString(), argumentCaptor.getValue().getValue("status"));
    }

    @Test
    void addSoftwareNullId() throws Exception {
        SoftwareRequest softwareRequest = new SoftwareRequest().softwareId(null).softwareName("Project1").status(SoftwareRequest.StatusEnum.TESTING).softwareVersion("1.0").projectId(1).teamId(1);
        this.softwareDao.addSoftware(softwareRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq
                        ("insert into software (softwareId, softwareName, softwareVersion, team, project, status) values (:softwareId, :softwareName, :softwareVersion, :team, :project, :status)")
                , argumentCaptor.capture());
        assertEquals(softwareRequest.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
        assertEquals(softwareRequest.getSoftwareName(), argumentCaptor.getValue().getValue("softwareName"));
        assertEquals(softwareRequest.getSoftwareVersion(), argumentCaptor.getValue().getValue("softwareVersion"));
        assertEquals(softwareRequest.getTeamId(), argumentCaptor.getValue().getValue("team"));
        assertEquals(softwareRequest.getProjectId(), argumentCaptor.getValue().getValue("project"));
        assertEquals(softwareRequest.getStatus().toString(), argumentCaptor.getValue().getValue("status"));
    }

    @Test
    void addNullSoftware() {
        SoftwareRequest softwareRequest = null;
        assertThrows(Exception.class, () -> {
            this.softwareDao.addSoftware(softwareRequest);
        });
    }

    @Test
    void updateSoftware() throws Exception {
        SoftwareRequest softwareRequest = new SoftwareRequest().softwareId("GGG").softwareName("Project1").status(SoftwareRequest.StatusEnum.TESTING).softwareVersion("1.0").projectId(1).teamId(1);

        this.softwareDao.updateSoftware("GGG", softwareRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update software set softwareName = :softwareName, softwareVersion = :softwareVersion, team = :teamId, project = :projectId, status = :status where softwareId = :softwareId")
                , argumentCaptor.capture());

        assertEquals(softwareRequest.getSoftwareId(), argumentCaptor.getValue().getValue("softwareId"));
        assertEquals(softwareRequest.getSoftwareName(), argumentCaptor.getValue().getValue("softwareName"));
        assertEquals(softwareRequest.getSoftwareVersion(), argumentCaptor.getValue().getValue("softwareVersion"));
        assertEquals(softwareRequest.getTeamId(), argumentCaptor.getValue().getValue("teamId"));
        assertEquals(softwareRequest.getProjectId(), argumentCaptor.getValue().getValue("projectId"));
        assertEquals(softwareRequest.getStatus().toString(), argumentCaptor.getValue().getValue("status"));
    }

    @Test
    void updateNullSoftware() {
        SoftwareRequest softwareRequest = null;
        assertThrows(Exception.class, () -> {
            this.softwareDao.updateSoftware(null, softwareRequest);
        });
    }

    @Test
    void deleteSoftwareById() throws Exception {
        this.softwareDao.deleteSoftwareById("GGG");
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from Software where softwareId = :softwareId")
                , argumentCaptor.capture());
        assertEquals("GGG", argumentCaptor.getValue().getValue("softwareId"));
    }
}
