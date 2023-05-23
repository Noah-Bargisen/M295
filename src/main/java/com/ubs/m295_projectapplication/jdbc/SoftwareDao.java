package com.ubs.m295_projectapplication.jdbc;

import com.ubs.module.Software;
import com.ubs.module.Team;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.List;

public class SoftwareDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public SoftwareDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<Software> getAllSoftwars() {
        String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Software.class));
    }


    public Software getSoftwareById(int softwareId) {
        String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE softwareId = :softwareId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("softwareId", softwareId);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(Software.class));
    }

    public void addSoftware(Software software) {

        String sql = "insert into software (softwareId, team, project, status) values (:softwareId, :team, :project, :status)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("softwareId", software.getSoftwareId())
                .addValue("team", software.getTeam().getTeamId())
                .addValue("project", software.getProject().getProjectId())
                .addValue("status", software.getStatus().toString());
        int status = namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public int updateSoftware(Software software) {
        String sql = "update software set softwareName = :softwareName, softwareVersion = :softwareVersion, team = :teamId, project = :projectId, status = :status where teamId = :teamId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("softwareName", software.getSoftwareName())
                .addValue("softwareVersion", software.getSoftwareVersion())
                .addValue("teamId", software.getTeam().getTeamId())
                .addValue("projectId", software.getProject().getProjectId())
                .addValue("status", software.getStatus().toString());
        return namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public int deleteTeamById(int softwareId) {
        String sql = "delete from Software where softwareId = :softwareId";
        SqlParameterSource paramSource = new MapSqlParameterSource("softwareId", softwareId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
    }

}
