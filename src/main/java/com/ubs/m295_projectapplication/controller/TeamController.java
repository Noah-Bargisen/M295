package com.ubs.m295_projectapplication.controller;

import com.ubs.m295_projectapplication.jdbc.TeamDao;
import com.ubs.gen.module.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/m295")
@Slf4j
public class TeamController {

    private final static String GET_ALL_TEAM_PATH = "/team";
    private final static String GET_ONE_TEAM_PATH = "/team/{teamId}";
    private final static String ADMIN_POST_TEAM_PATH = "/admin/team";
    private final static String ADMIN_PUT_TEAM_PATH = "/admin/team";
    private final static String ADMIN_DELETE_TEAM_PATH = "/admin/team/{teamId}";

    private final TeamDao teamDao;

    public TeamController(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @GetMapping(GET_ALL_TEAM_PATH)
    public @ResponseBody ResponseEntity getAllTeams() {
        log.info("Getting all teams");
        try {
            List<Team> teamList = teamDao.getAllTeams();
            return ResponseEntity.ok().body(teamList);
        } catch (SQLException e) {
            log.warn("Error getting teams", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting teams", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(GET_ONE_TEAM_PATH)
    public @ResponseBody ResponseEntity getOneTeam(@PathVariable("teamId") int teamId) {
        log.info("Getting one team");
        try {
            Team team = teamDao.getTeamById(teamId);
            return ResponseEntity.ok().body(team);
        } catch (SQLException e) {
            log.warn("Error getting team", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting team", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(ADMIN_POST_TEAM_PATH)
    public ResponseEntity postTeam(@RequestBody Team team) {
        log.info("Posting one team");
        try {
            int status = teamDao.addTeam(team);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error posting team", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error posting team", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(ADMIN_PUT_TEAM_PATH)
    public ResponseEntity putTeam(@RequestBody Team team) {
        log.info("Putting one team");
        try {
            int status = teamDao.updateTeam(team);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error putting team", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error putting team", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(ADMIN_DELETE_TEAM_PATH)
    public ResponseEntity deleteTeam(@PathVariable("teamId") int teamId) {
        log.info("Deleting one team");
        try {
            int status = teamDao.deleteTeamById(teamId);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error deleting team", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error deleting team", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
