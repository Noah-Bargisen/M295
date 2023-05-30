package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.TeamSetExtractor;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TeamDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final GeneratedKeyHolder generatedKeyHolder;

    public TeamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, GeneratedKeyHolder generatedKeyHolder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = generatedKeyHolder;
    }

    private List<TeamMember> teamMemberList = new ArrayList<>();

    public List<Team> getAllTeams() throws Exception {
        try {
            String sql = "select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team";
            return namedParameterJdbcTemplate.query(sql, new TeamSetExtractor());
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Teams not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting teams.", exception);
        }
    }


    public Team getTeamById(int teamId) throws Exception {
        try {
            String sql = "select * from team t join software s on t.teamId = s.team join project p on s.project = p.projectId join teammember tm on t.teamId = tm.team WHERE teamId = :teamId";
            SqlParameterSource namedParameters = new MapSqlParameterSource("teamId", teamId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new TeamSetExtractor()).get(0);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting team.", exception);
        }
    }

    public int addTeam(Team team) throws Exception {
        try {
            String sql = "insert into team (teamName, budget) values (:teamName, :budget)";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("teamName", team.getTeamName())
                    .addValue("budget", team.getBudget());
            int status = namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
            int id = generatedKeyHolder.getKey().intValue();
            System.out.println(id);
            team.setTeamId(id);
            return status;
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team not added.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while adding team.", exception);
        }
    }

    public int updateTeam(Team team) throws Exception {
        try {
            String sql = "update team set teamName = :teamName, budget = :budget where teamId = :teamId";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("teamName", team.getTeamName())
                    .addValue("budget", team.getBudget())
                    .addValue("teamId", team.getTeamId());
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team not updated.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while updating team.", exception);
        }
    }

    public int deleteTeamById(int teamId) throws Exception {
        try {
            String sql = "delete from team where teamId = :teamId";
            SqlParameterSource paramSource = new MapSqlParameterSource("teamId", teamId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team not deleted.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while deleting team.", exception);
        }
    }
}
