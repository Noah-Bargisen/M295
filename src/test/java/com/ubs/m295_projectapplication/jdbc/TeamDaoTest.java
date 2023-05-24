package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.m295_projectapplication.service.extractor.TeamSetExtractor;
import com.ubs.gen.module.Project;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamMember;
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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private GeneratedKeyHolder generatedKeyHolder;

    private TeamDao teamDao;

    @BeforeEach
    void setUp() {
        this.teamDao = new TeamDao(this.namedParameterJdbcTemplate, generatedKeyHolder);
    }

    @Test
    void getAllTeams() throws SQLException {
        this.teamDao.getAllTeams();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team")
                , any(TeamSetExtractor.class));
    }

    @Test
    void getTeamById() throws SQLException {
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Team1");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(TeamSetExtractor.class)))
                .thenReturn(List.of(team));
        this.teamDao.getTeamById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq("select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team WHERE teamId = :teamId")
                , argumentCaptor.capture(), any(TeamSetExtractor.class));
        assertEquals(1, argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void addTeam() throws SQLException {
        TeamMember teamMember = new TeamMember();
        teamMember.setName("TeamMember1");
        teamMember.setJoinDate(OffsetDateTime.now());
        teamMember.setFirstname("TeamMember1");
        teamMember.setMemberId(1L);
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Team1");
        team.setBudget(1000.00);
        teamMember.setTeam(team);
        team.setTeamMembers(List.of(teamMember));

        when(this.generatedKeyHolder.getKey())
                .thenReturn(1);
        this.teamDao.addTeam(team);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("insert into team (teamName, budget) values (:teamName, :budget)")
                , argumentCaptor.capture(), any(GeneratedKeyHolder.class));
        assertEquals(team.getTeamName(), argumentCaptor.getValue().getValue("teamName"));
        assertEquals(team.getBudget(), argumentCaptor.getValue().getValue("budget"));
    }

    @Test
    void updateTeam() throws SQLException {
        TeamMember teamMember = new TeamMember();
        teamMember.setName("TeamMember1");
        teamMember.setJoinDate(OffsetDateTime.now());
        teamMember.setFirstname("TeamMember1");
        teamMember.setMemberId(1L);
        Team team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Team1");
        team.setBudget(1000.00);
        teamMember.setTeam(team);
        team.setTeamMembers(List.of(teamMember));
        this.teamDao.updateTeam(team);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update team set teamName = :teamName, budget = :budget where teamId = :teamId")
                , argumentCaptor.capture());

        assertEquals(team.getTeamId(), argumentCaptor.getValue().getValue("teamId"));
        assertEquals(team.getTeamName(), argumentCaptor.getValue().getValue("teamName"));
        assertEquals(team.getBudget(), argumentCaptor.getValue().getValue("budget"));
    }

    @Test
    void deleteTeamById() throws SQLException {
        this.teamDao.deleteTeamById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from project where projectId = :projectId")
                , argumentCaptor.capture());
        assertEquals(1, argumentCaptor.getValue().getValue("projectId"));
    }
}