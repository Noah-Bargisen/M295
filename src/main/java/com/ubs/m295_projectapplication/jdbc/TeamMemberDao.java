package com.ubs.m295_projectapplication.jdbc;

import com.ubs.module.Team;
import com.ubs.module.TeamMember;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class TeamMemberDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public TeamMemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TeamMember> getAllTeamMember() {
        String sql = "select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TeamMember.class));
    }


    public TeamMember getTeamMemberById(int memberId) {
        String sql = "select * from teammember tm join team t on tm.team = t.teamId join software s on t.teamId = s.team join project p on s.project = p.projectId WHERE memberId = :memberId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(TeamMember.class));
    }

    public void addTeamMember(TeamMember teamMember) {
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
    }

    public int updateTeamMember(TeamMember teamMember) {
        String sql = "update TEAMMEMBER set name = :name, firstname = :firstname, joinDate = :joinDate, team = :teamId where memberId = :memberId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", teamMember.getName())
                .addValue("firstname", teamMember.getFirstname())
                .addValue("joinDate", teamMember.getJoinDate())
                .addValue("teamId", teamMember.getTeam().getTeamId())
                .addValue("memberId", teamMember.getMemberId());
        return namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public int deleteTeamMemberById(int memberId) {
        String sql = "delete from TEAMMEMBER where memberId = :memberId";
        SqlParameterSource paramSource = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
    }


}
