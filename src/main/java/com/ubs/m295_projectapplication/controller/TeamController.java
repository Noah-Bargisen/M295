package com.ubs.m295_projectapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.gen.controller.TeamApi;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamRequest;
import com.ubs.m295_projectapplication.jdbc.TeamDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class TeamController extends AbstractController implements TeamApi {

    private final TeamDao teamDao;
    private final TeamMemberDao teamMemberDao;

    public TeamController(TeamDao teamDao, TeamMemberDao teamMemberDao) {
        this.teamDao = teamDao;
        this.teamMemberDao = teamMemberDao;
    }
    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return TeamApi.super.getObjectMapper();
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return TeamApi.super.getRequest();
    }

    @Override
    public Optional<String> getAcceptHeader() {
        return TeamApi.super.getAcceptHeader();
    }


    @Override
    public ResponseEntity<List<Team>> getTeams() {
        try {
            if(log.isDebugEnabled()) {
                log.debug("Getting teams");
            }
            log.info("Getting teams...");
            List<Team> teams = teamDao.getAllTeams();
            log.info("Teams retrieved...");
            return okRespond(teams);
        } catch (DataAccessException exception) {
            log.warn("Error getting teams", exception);
            throwBadRequest("Error getting teams", exception);
        } catch (Exception exception) {
            log.error("Critical error getting teams", exception);
            throwInternalServerError("Critical error getting teams", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Integer> createTeam(TeamRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating team: {}", body);
            }
            log.info("Creating team...");
            int id = teamDao.addTeam(body);
            log.info("Team created...");
            return okRespond(id);
        } catch (SQLException exception) {
            log.warn("Error creating team", exception);
            throwBadRequest("Error creating team", exception);
        } catch (Exception exception) {
            log.error("Critical error creating team", exception);
            throwInternalServerError("Critical error creating team", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Team> deleteTeam(Integer teamId) {
        try{
            if(log.isDebugEnabled()) {
                log.debug("Deleting team: {}", teamId);
            }
            log.info("Deleting team...");
            teamMemberDao.getAllTeamMemberByTeamId(teamId).forEach(teamMember -> {
                try {
                    teamMemberDao.deleteTeamMemberById(teamMember.getMemberId());
                } catch (Exception e) {
                    throw new DataAccessException("Error deleting team member", e) {
                    };
                }
            });
            teamDao.deleteTeamById(teamId);
            log.info("Team deleted...");
            return okRespond(null);
        } catch (DataAccessException exception) {
            log.warn("Error deleting team", exception);
            throwBadRequest("Error deleting team", exception);
        } catch (Exception exception) {
            throwInternalServerError("Critical error deleting team", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Team> getTeam(Integer teamId) {
        try {
            if(log.isDebugEnabled()) {
                log.debug("Getting team: {}", teamId);
            }
            log.info("Getting team...");
            Team team = teamDao.getTeamById(teamId);
            log.info("Team retrieved...");
            return okRespond(team);
        } catch (DataAccessException exception) {
            log.warn("Error getting team", exception);
            throwBadRequest("Error getting team", exception);
        } catch (Exception exception) {
            log.error("Critical error getting team", exception);
            throwInternalServerError("Critical error getting team", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Team> updateTeam(Integer teamId, TeamRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Updating team: {}", body);
            }
            log.info("Updating team...");

            teamDao.updateTeam(teamId, body);
            log.info("Team updated...");
            return okRespond(teamDao.getTeamById(teamId));
        } catch (DataAccessException exception) {
            log.warn("Error updating team", exception);
            log.warn("Error updating team", exception);
        } catch (Exception exception) {
            log.error("Critical error updating team", exception);
            throwInternalServerError("Critical error updating team", exception);
        }
        return null;
    }
}
