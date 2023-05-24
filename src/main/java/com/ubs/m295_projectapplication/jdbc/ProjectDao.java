package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.module.Project;
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
public class ProjectDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public ProjectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private List<TeamMember> teamMemberList = new ArrayList<>();

    public List<Project> getAllProjects() throws SQLException {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team";
        return namedParameterJdbcTemplate.query(sql, new ProjectSetExtractor());
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Projects not found.");
        }
    }


    public Project getProjectById(int projectId) throws SQLException {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team where projectId = :projectId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectId", projectId);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectSetExtractor()).get(0);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Project not found.");
        }
    }

    public void addProject(Project project) throws SQLException {
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
            log.debug(e.getMessage());
            throw new SQLException("Project not added.");
        }
    }

    public int updateProject(Project project) throws SQLException {
        try {
        String sql = "update project set projectName = :projectName where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("projectName", project.getProjectName())
                .addValue("projectId", project.getProjectId());
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Project not updated.");
        }
    }

    public int deleteTeamById(int projectId) throws SQLException {
        try {
        String sql = "delete from project where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource("projectId", projectId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new SQLException("Project not deleted.");
        }
    }
}
