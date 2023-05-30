package com.ubs.m295_projectapplication.jdbc;

import com.ubs.m295_projectapplication.service.extractor.SoftwareSetExtractor;
import com.ubs.gen.module.Software;
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
public class SoftwareDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SoftwareDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<Software> getAllSoftware() throws Exception {
        try {
            List<TeamMember> teamMembers = new ArrayList<>();
            String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId";
            return namedParameterJdbcTemplate.query(sql, new SoftwareSetExtractor());
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Softwares not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting softwares.", exception);
        }
    }


    public Software getSoftwareById(String softwareId) throws Exception {
        try {
            List<TeamMember> teamMembers = new ArrayList<>();
            String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE softwareId = :softwareId";
            SqlParameterSource namedParameters = new MapSqlParameterSource("softwareId", softwareId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new SoftwareSetExtractor()).get(0);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting software.", exception);
        }

    }

    public int addSoftware(Software software) throws Exception {
        try {
            String sql = "insert into software (softwareId, team, project, status) values (:softwareId, :team, :project, :status)";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("softwareId", software.getSoftwareId())
                    .addValue("team", software.getTeam().getTeamId())
                    .addValue("project", software.getProject().getProjectId())
                    .addValue("status", software.getStatus().toString());
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not added.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while adding software.", exception);
        }
    }

    public int updateSoftware(Software software) throws Exception {
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
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not updated.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while updating software.", exception);
        }
    }

    public int deleteSoftwareById(String softwareId) throws Exception {
        try {
            String sql = "delete from Software where softwareId = :softwareId";
            SqlParameterSource paramSource = new MapSqlParameterSource("softwareId", softwareId);
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not deleted.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while deleting software.", exception);
        }
    }
}
