package com.ubs.m295_projectapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.gen.controller.TeamMemberApi;
import com.ubs.gen.module.TeamMember;
import com.ubs.gen.module.TeamMemberRequest;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class TeamMemberController extends AbstractController implements TeamMemberApi {


    private final TeamMemberDao teamMemberDao;

    public TeamMemberController(TeamMemberDao teamMemberDao) {
        this.teamMemberDao = teamMemberDao;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return TeamMemberApi.super.getObjectMapper();
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return TeamMemberApi.super.getRequest();
    }

    @Override
    public Optional<String> getAcceptHeader() {
        return TeamMemberApi.super.getAcceptHeader();
    }



    @Override
    public ResponseEntity<List<TeamMember>> getTeamMembers() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting team members");
            }
            log.info("Getting team members...");
            List<TeamMember> teamMembers = teamMemberDao.getAllTeamMember();
            log.info("Team members retrieved...");
            return okRespond(teamMembers);
        } catch (SQLException exception) {
            log.warn("Error getting team members", exception);
            throwBadRequest("Error getting team members", exception);
        }
        catch (Exception exception) {
            log.error("Critical error getting team members", exception);
            throwBadRequest("Error getting team members", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Integer> createTeamMember(TeamMemberRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating team member");
            }
            log.info("Creating team member...");
            int id = teamMemberDao.addTeamMember(body);
            log.info("Team member created...");
            return okRespond(id);
        } catch (SQLException exception) {
            log.warn("Error creating team member", exception);
            throwBadRequest("Error creating team member", exception);
        } catch (Exception exception) {
            log.error("Critical error creating team member", exception);
            throwInternalServerError("Critical error creating team member", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Integer> deleteTeamMember(Integer memberId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Deleting team member");
            }
            log.info("Deleting team member...");
            int id = teamMemberDao.deleteTeamMemberById(memberId);
            log.info("Team member deleted...");
            return okRespond(id);
        } catch (SQLException exception) {
            log.warn("Error deleting team member", exception);
            throwBadRequest("Error deleting team member", exception);
        } catch (Exception exception) {
            log.error("Critical error deleting team member", exception);
            throwInternalServerError("Critical error deleting team member", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<TeamMember> getTeamMember(Integer memberId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting team member");
            }
            log.info("Getting team member...");
            TeamMember teamMember = teamMemberDao.getTeamMemberById(memberId);
            log.info("Team member retrieved...");
            return okRespond(teamMember);
        } catch (SQLException exception) {
            log.warn("Error getting team member", exception);
            throwBadRequest("Error getting team member", exception);
        } catch (Exception exception) {
            log.error("Critical error getting team member", exception);
            throwInternalServerError("Critical error getting team member", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<TeamMember> updateTeamMember(Integer memberId, TeamMemberRequest body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Updating team member");
            }
            log.info("Updating team member...");
            teamMemberDao.updateTeamMember(memberId, body);
            log.info("Team member updated...");
            return okRespond(teamMemberDao.getTeamMemberById(memberId));
        } catch (SQLException exception) {
            log.warn("Error updating team member", exception);
            throwBadRequest("Error updating team member", exception);
        } catch (Exception exception) {
            log.error("Critical error updating team member", exception);
            throwInternalServerError("Critical error updating team member", exception);
        }
        return null;
    }
}
