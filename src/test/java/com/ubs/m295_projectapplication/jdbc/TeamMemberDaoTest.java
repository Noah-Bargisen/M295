package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.TeamMember;
import com.ubs.gen.module.TeamMemberRequest;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamMemberDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private GeneratedKeyHolder generatedKeyHolder;


    private TeamMemberDao teamMemberDao;
    @BeforeEach
    void setUp() {
        this.teamMemberDao = new TeamMemberDao(this.namedParameterJdbcTemplate, generatedKeyHolder);
    }

    @Test
    void getAllTeamMembers() throws Exception {
        this.teamMemberDao.getAllTeamMember();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from teammember tm join team t on tm.team = t.teamId")
                , any(TeamMemberSetExtractor.class));
    }

    @Test
    void getTeamMemberById() throws Exception {
        TeamMember teamMember = new TeamMember();
        teamMember.setMemberId(1);
        teamMember.setFirstname("Project1");
        teamMember.setName("Project2");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(TeamMemberSetExtractor.class)))
                .thenReturn(List.of(teamMember));
        this.teamMemberDao.getTeamMemberById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from teammember tm join team t on tm.team = t.teamId WHERE memberId = :memberId")
                ,argumentCaptor.capture(), any(TeamMemberSetExtractor.class));

        assertEquals(1, argumentCaptor.getValue().getValue("memberId"));

    }


    @Test
    void getTeamMemberByTeamId() throws Exception {
        TeamMember teamMember = new TeamMember();
        teamMember.setMemberId(1);
        teamMember.setFirstname("Project1");
        teamMember.setName("Project2");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(TeamMemberSetExtractor.class)))
                .thenReturn(List.of(teamMember));
        this.teamMemberDao.getAllTeamMemberByTeamId(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from teammember tm join team t on tm.team = t.teamId WHERE team = :team")
                ,argumentCaptor.capture(), any(TeamMemberSetExtractor.class));

        assertEquals(1, argumentCaptor.getValue().getValue("team"));

    }

    @Test
    void addTeamMember() throws Exception {
        TeamMemberRequest teamMemberRequest = new TeamMemberRequest().firstname("firstname").name("lastname").joinDate(OffsetDateTime.now()).teamId(1);
        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.teamMemberDao.addTeamMember(teamMemberRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq
                        ("insert into TEAMMEMBER (name, firstname, joinDate, team) values (:name, :firstname, :joinDate, :teamId)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(teamMemberRequest.getName(), argumentCaptor.getValue().getValue("name"));
        assertEquals(teamMemberRequest.getFirstname(), argumentCaptor.getValue().getValue("firstname"));
        assertEquals(teamMemberRequest.getJoinDate(), argumentCaptor.getValue().getValue("joinDate"));
        assertEquals(teamMemberRequest.getTeamId(), argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void addNullTeamMember() throws Exception {
        TeamMemberRequest teamMemberRequest = null;
        assertThrows(Exception.class, () -> {
            this.teamMemberDao.addTeamMember(teamMemberRequest);
        });
    }

    @Test
    void updateTeamMember() throws Exception {
        TeamMemberRequest teamMemberRequest = new TeamMemberRequest();
        teamMemberRequest.setFirstname("Project1");
        teamMemberRequest.setName("Project2");
        teamMemberRequest.setTeamId(1);
        teamMemberRequest.setJoinDate(OffsetDateTime.now());
        this.teamMemberDao.updateTeamMember(1, teamMemberRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update TEAMMEMBER set name = :name, firstname = :firstname, joinDate = :joinDate, team = :teamId where memberId = :memberId")
                , argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getValue("memberId"));
        assertEquals(teamMemberRequest.getName(), argumentCaptor.getValue().getValue("name"));
        assertEquals(teamMemberRequest.getFirstname(), argumentCaptor.getValue().getValue("firstname"));
        assertEquals(teamMemberRequest.getJoinDate(), argumentCaptor.getValue().getValue("joinDate"));
        assertEquals(teamMemberRequest.getTeamId(), argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void updateNullTeamMember() throws Exception {
        TeamMemberRequest teamMemberRequest = null;
        assertThrows(Exception.class, () -> {
            this.teamMemberDao.updateTeamMember(null, teamMemberRequest);
        });

    }

    @Test
    void deleteTeamMemberById() throws Exception {
        this.teamMemberDao.deleteTeamMemberById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from TEAMMEMBER where memberId = :memberId")
                , argumentCaptor.capture());
        assertEquals(1, argumentCaptor.getValue().getValue("memberId"));
    }
}
