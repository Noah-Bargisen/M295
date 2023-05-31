package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamRequest;
import com.ubs.m295_projectapplication.service.extractor.TeamSetExtractor;
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
    void getAllTeams() throws Exception {
        this.teamDao.getAllTeams();
        verify(this.namedParameterJdbcTemplate).query(eq
                        ("select * from teammember tm join team t on tm.team = t.teamId")
                , any(TeamSetExtractor.class));
    }

    @Test
    void getTeamById() throws Exception {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("Team1");
        when(this.namedParameterJdbcTemplate.query(anyString(), (SqlParameterSource) any(), any(TeamSetExtractor.class)))
                .thenReturn(List.of(team));
        this.teamDao.getTeamById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).query(eq("select * from team t join teammember tm on t.teamId = tm.team WHERE teamId = :teamId")
                , argumentCaptor.capture(), any(TeamSetExtractor.class));
        assertEquals(1, argumentCaptor.getValue().getValue("teamId"));
    }

    @Test
    void addTeam() throws Exception {
        TeamRequest team = new TeamRequest();
        team.setTeamName("Team1");
        team.setBudget(1000.00);
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
    void addNullTeam() {
        TeamRequest team = null;
        assertThrows(Exception.class, () -> {
            this.teamDao.addTeam(team);
        });
    }
    @Test
    void updateTeam() throws Exception {

        TeamRequest teamRequest = new TeamRequest();
        teamRequest.setTeamName("Team1");
        teamRequest.setBudget(1000.00);

        this.teamDao.updateTeam(1, teamRequest);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("update team set teamName = :teamName, budget = :budget where teamId = :teamId")
                , argumentCaptor.capture());

        assertEquals(1, argumentCaptor.getValue().getValue("teamId"));
        assertEquals(teamRequest.getTeamName(), argumentCaptor.getValue().getValue("teamName"));
        assertEquals(teamRequest.getBudget(), argumentCaptor.getValue().getValue("budget"));
    }

    @Test
    void updateNullTeam() throws Exception {

        TeamRequest teamRequest = null;
        assertThrows(Exception.class, () -> {
            this.teamDao.updateTeam(null, teamRequest);
        });

    }

    @Test
    void deleteTeamById() throws Exception {
        this.teamDao.deleteTeamById(1);
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate).update(eq("delete from team where teamId = :teamId")
                , argumentCaptor.capture());
        assertEquals(1, argumentCaptor.getValue().getValue("teamId"));
    }
}