package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.TeamSetExtractor;
import com.ubs.module.Team;
import com.ubs.module.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TeamDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public TeamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private List<TeamMember> teamMemberList = new ArrayList<>();

    public List<Team> getAllTeams() throws SQLException {
        try {
        String sql = "select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team";
        return namedParameterJdbcTemplate.query(sql, new TeamSetExtractor());
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Teams not found.");
        }
    }


    public Team getTeamById(int teamId) throws SQLException {
        try {
        String sql = "select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team WHERE teamId = :teamId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("teamId", teamId);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new TeamSetExtractor()).get(0);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team not found.");
        }
    }

    public void addTeam(Team team) throws SQLException {
        try {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "insert into team (teamName, budget) values (:teamName, :budget)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("teamName", team.getTeamName())
                .addValue("budget", team.getBudget());
        int status = namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        int id = generatedKeyHolder.getKey().intValue();
        System.out.println(id);
        team.setTeamId((long) id);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team not added.");
        }
    }

    public int updateTeam(Team team) throws SQLException {
        try {
            String sql = "update team set teamName = :teamName, budget = :budget where teamId = :teamId";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("teamName", team.getTeamName())
                    .addValue("budget", team.getBudget())
                    .addValue("teamId", team.getTeamId());
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team not updated.");
        }
    }

    public int deleteTeamById(int teamId) throws SQLException {
        try {
            String sql = "delete from team where teamId = :teamId";
            SqlParameterSource paramSource = new MapSqlParameterSource("teamId", teamId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team not deleted.");
        }
    }
}
