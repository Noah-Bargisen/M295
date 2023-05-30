package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Project;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamMember;
import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void getAllProjects() throws Exception {
        this.teamMemberDao.getAllTeamMember();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId")
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
                        ("select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId WHERE memberId = :memberId")
                ,argumentCaptor.capture(), any(TeamMemberSetExtractor.class));

        assertEquals(1, argumentCaptor.getValue().getValue("memberId"));

    }

    @Test
    void addTeamMember() throws Exception {
        TeamMember teamMember = new TeamMember().memberId(1).firstname("firstname").name("lastname").joinDate(OffsetDateTime.now()).team(new Team().teamId(1).budget(100.00));
        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.teamMemberDao.addTeamMember(teamMember);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq
                        ("insert into TEAMMEMBER (name, firstname, joinDate, team) values (:name, :firstname, :joinDate, :teamId)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(teamMember.getName(), argumentCaptor.getValue().getValue("name"));
        assertEquals(teamMember.getFirstname(), argumentCaptor.getValue().getValue("firstname"));
        assertEquals(teamMember.getJoinDate(), argumentCaptor.getValue().getValue("joinDate"));
        assertEquals(teamMember.getTeam().getTeamId(), argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void updateProject() throws Exception {
        TeamMember teamMember = new TeamMember();
        teamMember.setMemberId(1);
        teamMember.setFirstname("Project1");
        teamMember.setName("Project2");
        teamMember.setTeam(new Team().teamId(1).budget(100.00));
        teamMember.setJoinDate(OffsetDateTime.now());
        this.teamMemberDao.updateTeamMember(teamMember);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update TEAMMEMBER set name = :name, firstname = :firstname, joinDate = :joinDate, team = :teamId where memberId = :memberId")
                , argumentCaptor.capture());

        assertEquals(teamMember.getMemberId(), argumentCaptor.getValue().getValue("memberId"));
        assertEquals(teamMember.getName(), argumentCaptor.getValue().getValue("name"));
        assertEquals(teamMember.getFirstname(), argumentCaptor.getValue().getValue("firstname"));
        assertEquals(teamMember.getJoinDate(), argumentCaptor.getValue().getValue("joinDate"));
        assertEquals(teamMember.getTeam().getTeamId(), argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void deleteProjectById() throws Exception {
        this.teamMemberDao.deleteTeamMemberById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from TEAMMEMBER where memberId = :memberId")
                , argumentCaptor.capture());
        assertEquals(1, argumentCaptor.getValue().getValue("memberId"));
    }
}
