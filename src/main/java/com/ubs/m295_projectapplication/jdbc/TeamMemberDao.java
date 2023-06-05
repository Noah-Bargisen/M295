package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.TeamMember;
import com.ubs.gen.module.TeamMemberRequest;
import com.ubs.m295_projectapplication.service.extractor.TeamMemberSetExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class TeamMemberDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final GeneratedKeyHolder generatedKeyHolder;

    public TeamMemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, GeneratedKeyHolder generatedKeyHolder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = generatedKeyHolder;
    }

    public List<TeamMember> getAllTeamMember() throws Exception {
        try {
            String sql = "select * from teammember tm join team t on tm.team = t.teamId";
            return namedParameterJdbcTemplate.query(sql, new TeamMemberSetExtractor());
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team members not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting team members.", exception);
        }
    }


    public TeamMember getTeamMemberById(int memberId) throws Exception {
        try {
            String sql = "select * from teammember tm join team t on tm.team = t.teamId WHERE memberId = :memberId";
            SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new TeamMemberSetExtractor()).get(0);
        } catch (DataAccessException | IndexOutOfBoundsException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team member not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting team member.", exception);
        }
    }

    public List<TeamMember> getAllTeamMemberByTeamId(int teamId) throws Exception {
        try {
            String sql = "select * from teammember tm join team t on tm.team = t.teamId WHERE team = :team";
            SqlParameterSource namedParameters = new MapSqlParameterSource("team", teamId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new TeamMemberSetExtractor());
        } catch (DataAccessException | IndexOutOfBoundsException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team members not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting team member.", exception);
        }
    }

    public int addTeamMember(TeamMemberRequest teamMemberRequest) throws Exception {
        try {
            String sql = "insert into TEAMMEMBER (name, firstname, joinDate, team) values (:name, :firstname, :joinDate, :teamId)";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("name", teamMemberRequest.getName())
                    .addValue("firstname", teamMemberRequest.getFirstname())
                    .addValue("joinDate", teamMemberRequest.getJoinDate())
                    .addValue("teamId", teamMemberRequest.getTeamId());
            namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
            int id = generatedKeyHolder.getKey().intValue();
            return id;
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team member not added.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical server error while adding team member.", exception);
        }
    }

    public int updateTeamMember(Integer teamMemberId, TeamMemberRequest teamMemberRequest) throws Exception {
        try {
            String sql = "update TEAMMEMBER set name = :name, firstname = :firstname, joinDate = :joinDate, team = :teamId where memberId = :memberId";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("name", teamMemberRequest.getName())
                    .addValue("firstname", teamMemberRequest.getFirstname())
                    .addValue("joinDate", teamMemberRequest.getJoinDate())
                    .addValue("teamId", teamMemberRequest.getTeamId())
                    .addValue("memberId", teamMemberId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team member not updated.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical server error while updating team member.", exception);
        }
    }

    public int deleteTeamMemberById(int memberId) throws Exception {
        try {
            String sql = "delete from TEAMMEMBER where memberId = :memberId";
            SqlParameterSource paramSource = new MapSqlParameterSource("memberId", memberId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Team member not deleted.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical server error while deleting team member.", exception);
        }
    }
}
