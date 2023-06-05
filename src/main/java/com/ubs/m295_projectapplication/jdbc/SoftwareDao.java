package com.ubs.m295_projectapplication.jdbc;

import com.ubs.gen.module.Software;
import com.ubs.gen.module.SoftwareRequest;
import com.ubs.gen.module.TeamMember;
import com.ubs.m295_projectapplication.service.extractor.SoftwareSetExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

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
        } catch (DataAccessException | IndexOutOfBoundsException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting software.", exception);
        }
    }

    public List<Software> getAllSoftwareByProjectId(int projectId) throws Exception {
        try {
            List<TeamMember> teamMembers = new ArrayList<>();
            String sql = "select * from software s join team t on s.team = t.teamId join teammember tm on t.teamId = tm.team join project p on s.project = p.projectId WHERE project = :project";
            SqlParameterSource namedParameters = new MapSqlParameterSource("project", projectId);
            return namedParameterJdbcTemplate.query(sql, namedParameters, new SoftwareSetExtractor());
        } catch (DataAccessException | IndexOutOfBoundsException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Softwares not found.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while getting software.", exception);
        }
    }

    public int addSoftware(SoftwareRequest softwareRequest) throws Exception {
        try {
            String sql = "insert into software (softwareId, softwareName, softwareVersion, team, project, status) values (:softwareId, :softwareName, :softwareVersion, :team, :project, :status)";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("softwareId", softwareRequest.getSoftwareId())
                    .addValue("softwareName", softwareRequest.getSoftwareName())
                    .addValue("softwareVersion", softwareRequest.getSoftwareVersion())
                    .addValue("team", softwareRequest.getTeamId())
                    .addValue("project", softwareRequest.getProjectId())
                    .addValue("status", softwareRequest.getStatus().toString());
            return namedParameterJdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.debug(exception.getMessage());
            throw new SQLException("Software not added.", exception);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
            throw new Exception("Critical error while adding software.", exception);
        }
    }

    public int updateSoftware(String softwareId, SoftwareRequest softwareRequest) throws Exception {
        try {
            String sql = "update software set softwareName = :softwareName, softwareVersion = :softwareVersion, team = :teamId, project = :projectId, status = :status where softwareId = :softwareId";
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue("softwareName", softwareRequest.getSoftwareName())
                    .addValue("softwareVersion", softwareRequest.getSoftwareVersion())
                    .addValue("teamId", softwareRequest.getTeamId())
                    .addValue("projectId", softwareRequest.getProjectId())
                    .addValue("status", softwareRequest.getStatus().toString())
                    .addValue("softwareId", softwareId);
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
