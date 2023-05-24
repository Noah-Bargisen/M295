package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.TeamMemberSetExtractor;
import com.ubs.module.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.SQLException;
import java.util.List;
@Slf4j
public class TeamMemberDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public TeamMemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TeamMember> getAllTeamMember() throws SQLException {
        try {
        String sql = "select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId";
        return namedParameterJdbcTemplate.query(sql, new TeamMemberSetExtractor());
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team members not found.");
        }
    }


    public TeamMember getTeamMemberById(int memberId) throws SQLException {
        try {
        String sql = "select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId WHERE memberId = :memberId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new TeamMemberSetExtractor()).get(0);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team member not found.");
        }
    }

    public void addTeamMember(TeamMember teamMember) throws SQLException {
        try {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "insert into TEAMMEMBER (name, firstname, joinDate, team) values (:name, :firstname, :joinDate, :teamId)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", teamMember.getName())
                .addValue("firstname", teamMember.getFirstname())
                .addValue("joinDate", teamMember.getJoinDate())
                .addValue("teamId", teamMember.getTeam().getTeamId());
        int status = namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        int id = generatedKeyHolder.getKey().intValue();
        System.out.println(id);
        teamMember.setMemberId((long) id);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team member not added.");
        }
    }

    public int updateTeamMember(TeamMember teamMember) throws SQLException {
        try {
        String sql = "update TEAMMEMBER set name = :name, firstname = :firstname, joinDate = :joinDate, team = :teamId where memberId = :memberId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", teamMember.getName())
                .addValue("firstname", teamMember.getFirstname())
                .addValue("joinDate", teamMember.getJoinDate())
                .addValue("teamId", teamMember.getTeam().getTeamId())
                .addValue("memberId", teamMember.getMemberId());
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team member not updated.");
        }
    }

    public int deleteTeamMemberById(int memberId) throws SQLException {
        try {
        String sql = "delete from TEAMMEMBER where memberId = :memberId";
        SqlParameterSource paramSource = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Team member not deleted.");
        }
    }
}
