package com.ubs.m295_projectapplication.controller;

import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import com.ubs.gen.module.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/m295")
@Slf4j
public class TeamMemberController {

    private final static String GET_ALL_TEAM_MEMBER_PATH = "/teamMember";
    private final static String GET_ONE_TEAM_MEMBER_PATH = "/teamMember/{teamMemberId}";

    private final static String ADMIN_POST_TEAM_MEMBER_PATH = "/admin/teamMember";
    private final static String ADMIN_PUT_TEAM_MEMBER_PATH = "/admin/teamMember";
    private final static String ADMIN_DELETE_TEAM_MEMBER_PATH = "/admin/teamMember/{teamMemberId}";

    private final TeamMemberDao teamMemberDao;

    public TeamMemberController(TeamMemberDao teamMemberDao) {
        this.teamMemberDao = teamMemberDao;
    }

    @GetMapping(GET_ALL_TEAM_MEMBER_PATH)
    public @ResponseBody ResponseEntity getAllTeamMembers() {
        log.info("Getting all team members");
        try {
            List<TeamMember> teamMemberList = teamMemberDao.getAllTeamMember();
            return ResponseEntity.ok().body(teamMemberList);
        } catch (SQLException e) {
            log.warn("Error getting team members", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting team members", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(GET_ONE_TEAM_MEMBER_PATH)
    public @ResponseBody ResponseEntity getOneTeamMember(@PathVariable("teamMemberId") int teamMemberId) {
        log.info("Getting one team member");
        try {
            TeamMember teamMember = teamMemberDao.getTeamMemberById(teamMemberId);
            return ResponseEntity.ok().body(teamMember);
        } catch (SQLException e) {
            log.warn("Error getting team member", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting team member", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(ADMIN_POST_TEAM_MEMBER_PATH)
    public ResponseEntity postTeamMember(@RequestBody TeamMember teamMember) {
        log.info("Posting one team member");
        try {
            int status = teamMemberDao.addTeamMember(teamMember);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error posting team member", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error posting team member", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(ADMIN_PUT_TEAM_MEMBER_PATH)
    public ResponseEntity putTeamMember(@RequestBody TeamMember teamMember) {
        log.info("Putting one team member");
        try {
            int status = teamMemberDao.updateTeamMember(teamMember);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error putting team member", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error putting team member", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(ADMIN_DELETE_TEAM_MEMBER_PATH)
    public ResponseEntity deleteTeamMember(@PathVariable("teamMemberId") int teamMemberId) {
        log.info("Deleting one team member");
        try {
            int status = teamMemberDao.deleteTeamMemberById(teamMemberId);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error deleting team member", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error deleting team member", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
