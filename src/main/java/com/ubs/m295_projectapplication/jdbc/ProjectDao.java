package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.ProjectSetExtractor;
import com.ubs.gen.module.Project;
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
import java.util.Objects;

@Slf4j
public class ProjectDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final GeneratedKeyHolder generatedKeyHolder;
    public ProjectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, GeneratedKeyHolder generatedKeyHolder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = generatedKeyHolder;
    }

    private List<TeamMember> teamMemberList = new ArrayList<>();

    public List<Project> getAllProjects() throws Exception {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team";
        return namedParameterJdbcTemplate.query(sql, new ProjectSetExtractor());
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Projects not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting projects.", exception);
        }
    }


    public Project getProjectById(int projectId) throws Exception {
        try {
        String sql = "select * from project p join software s on p.projectId = s.project join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team where projectId = :projectId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectId", projectId);
        return Objects.requireNonNull(namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectSetExtractor())).get(0);
        } catch (DataAccessException | IndexOutOfBoundsException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Project not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting project.", exception);
        }
    }

    public int addProject(Project project) throws Exception {
        try {
        String sql = "insert into project (projectName) values (:projectName)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("projectName", project.getProjectName());
        int status = namedParameterJdbcTemplate.update(sql, paramSource, this.generatedKeyHolder);
        int id = this.generatedKeyHolder.getKey().intValue();
        project.setProjectId(id);
        return status;
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Project not added.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while adding project.", exception);
        }
    }

    public int updateProject(Project project) throws Exception {
        try {
        String sql = "update project set projectName = :projectName where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("projectName", project.getProjectName())
                .addValue("projectId", project.getProjectId());
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Project not updated.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while updating project.", exception);
        }
    }

    public int deleteProjectById(int projectId) throws Exception {
        try {
        String sql = "delete from project where projectId = :projectId";
        SqlParameterSource paramSource = new MapSqlParameterSource("projectId", projectId);
        return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Project not deleted.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while deleting project.", exception);
        }
    }
}
