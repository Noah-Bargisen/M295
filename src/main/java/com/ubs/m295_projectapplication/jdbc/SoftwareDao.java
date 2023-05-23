package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.SoftwareSetExtractor;
import com.ubs.module.Software;
import com.ubs.module.TeamMember;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.List;

public class SoftwareDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SoftwareDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<Software> getAllSoftware() {
        try {
            List<TeamMember> teamMembers = new ArrayList<>();
            String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId";
            return namedParameterJdbcTemplate.query(sql, new SoftwareSetExtractor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Software getSoftwareById(int softwareId) {
        try {
            List<TeamMember> teamMembers = new ArrayList<>();
            String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE softwareId = :softwareId";
            SqlParameterSource namedParameters = new MapSqlParameterSource("softwareId", softwareId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new SoftwareSetExtractor()).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void addSoftware(Software software) {
        try {
            String sql = "insert into software (softwareId, team, project, status) values (:softwareId, :team, :project, :status)";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("softwareId", software.getSoftwareId())
                    .addValue("team", software.getTeam().getTeamId())
                    .addValue("project", software.getProject().getProjectId())
                    .addValue("status", software.getStatus().toString());
            int status = namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateSoftware(Software software) {
        try {
            String sql = "update software set softwareName = :softwareName, softwareVersion = :softwareVersion, team = :teamId, project = :projectId, status = :status where softwareId = :softwareId";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("softwareName", software.getSoftwareName())
                    .addValue("softwareVersion", software.getSoftwareVersion())
                    .addValue("teamId", software.getTeam().getTeamId())
                    .addValue("projectId", software.getProject().getProjectId())
                    .addValue("status", software.getStatus().toString())
                    .addValue("softwareId", software.getSoftwareId());
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteTeamById(int softwareId) {
        try {
            String sql = "delete from Software where softwareId = :softwareId";
            SqlParameterSource paramSource = new MapSqlParameterSource("softwareId", softwareId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
