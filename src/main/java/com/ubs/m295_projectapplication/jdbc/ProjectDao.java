package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.module.Project;
import com.ubs.module.TeamMember;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.ArrayList;
import java.util.List;

public class ProjectDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public ProjectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private List<TeamMember> teamMemberList = new ArrayList<>();

    public List<Project> getAllProjects() {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team";
        return namedParameterJdbcTemplate.query(sql, new ProjectSetExtractor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Project getProjectById(int projectId) {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team where projectId = :projectId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectId", projectId);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectSetExtractor()).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addProject(Project project) {
        try {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "insert into project (projectName) values (:projectName)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("projectName", project.getProjectName());
        int status = namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        int id = generatedKeyHolder.getKey().intValue();
        System.out.println(id);
        project.setProjectId((long) id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateProject(Project project) {
        try {
        String sql = "update project set projectName = :projectName where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("projectName", project.getProjectName())
                .addValue("projectId", project.getProjectId());
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteTeamById(int projectId) {
        try {
        String sql = "delete from project where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource("projectId", projectId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
