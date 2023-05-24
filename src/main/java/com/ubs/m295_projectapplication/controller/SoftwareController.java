package com.ubs.m295_projectapplication.controller;

import com.ubs.gen.controller.SoftwareApi;
import com.ubs.m295_projectapplication.jdbc.SoftwareDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import com.ubs.gen.module.Software;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/m295")
@Slf4j
public class SoftwareController extends AbstractController implements SoftwareApi {

    private final static String GET_ALL_SOFTWARE_PATH = "/software";
    private final static String GET_ONE_SOFTWARE_PATH = "/software/{softwareId}";

    private final static String ADMIN_POST_SOFTWARE_PATH = "/admin/software";
    private final static String ADMIN_PUT_SOFTWARE_PATH = "/admin/software";
    private final static String ADMIN_DELETE_SOFTWARE_PATH = "/admin/software/{softwareId}";

    private final SoftwareDao softwareDao;

    public SoftwareController(SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }

    @GetMapping(GET_ALL_SOFTWARE_PATH)
    public @ResponseBody ResponseEntity getAllSoftware() {
        log.info("Getting all software");
        try {
            List<Software> softwareList = softwareDao.getAllSoftware();
            return ResponseEntity.ok().body(softwareList);
        } catch (SQLException e) {
            log.warn("Error getting software", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting software", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(GET_ONE_SOFTWARE_PATH)
    public @ResponseBody ResponseEntity getOneSoftware(@PathVariable("softwareId") int softwareId) {
        log.info("Getting one software");
        try {
            Software software = softwareDao.getSoftwareById(softwareId);
            return ResponseEntity.ok().body(software);
        } catch (SQLException e) {
            log.warn("Error getting software", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error getting software", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(ADMIN_POST_SOFTWARE_PATH)
    public ResponseEntity postSoftware(@RequestBody Software software) {
        log.info("Posting one software");
        try {
            int status = softwareDao.addSoftware(software);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error posting software", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error posting software", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(ADMIN_PUT_SOFTWARE_PATH)
    public ResponseEntity putSoftware(@RequestBody Software software) {
        log.info("Putting one software");
        try {
            int status = softwareDao.updateSoftware(software);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error putting software", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error putting software", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(ADMIN_DELETE_SOFTWARE_PATH)
    public ResponseEntity deleteSoftware(@PathVariable("softwareId") int softwareId) {
        log.info("Deleting one software");
        try {
            int status = softwareDao.deleteTeamById(softwareId);
            return ResponseEntity.ok().body(status);
        } catch (SQLException e) {
            log.warn("Error deleting software", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Critical error deleting software", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<List<Software>> getSoftwares() {
        log.info("Getting all software");
        try {
            List<Software> softwareList = softwareDao.getAllSoftware();
            return ResponseEntity.ok().body(softwareList);
        } catch (SQLException e) {
            log.warn("Error getting software", e);
            throw badRequestRespond(e);
        } catch (Exception e) {
            log.error("Critical error getting software", e);
            throw internalServerErrorRespond(e);
        }
    }
}
