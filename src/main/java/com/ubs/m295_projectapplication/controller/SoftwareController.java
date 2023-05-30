package com.ubs.m295_projectapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.gen.controller.SoftwareApi;
import com.ubs.gen.module.Software;
import com.ubs.m295_projectapplication.jdbc.SoftwareDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class SoftwareController extends AbstractController implements SoftwareApi {

    private final SoftwareDao softwareDao;

    public SoftwareController(SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }


    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return SoftwareApi.super.getObjectMapper();
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return SoftwareApi.super.getRequest();
    }

    @Override
    public Optional<String> getAcceptHeader() {
        return SoftwareApi.super.getAcceptHeader();
    }




    @Override
    public ResponseEntity<List<Software>> getSoftwares() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting softwares");
            }
            log.info("Getting softwares...");
            List<Software> softwares = softwareDao.getAllSoftware();
            log.info("Softwares retrieved...");
        } catch (DataAccessException exception) {
            log.warn("Error getting softwares", exception);
            throwBadRequest("Error getting softwares", exception);
        } catch (Exception exception) {
            log.error("Critical error getting softwares", exception);
            throwInternalServerError("Critical error getting softwares", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Software> createSoftware(Software body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating software");
            }
            log.info("Creating software...");
            softwareDao.addSoftware(body);
            log.info("Software created...");
            return okRespond(null);
        } catch (DataAccessException exception) {
            log.warn("Error creating software", exception);
            throwBadRequest("Error creating software", exception);
        } catch (Exception exception) {
            log.error("Critical error creating software", exception);
            throwInternalServerError("Critical error creating software", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Software> deleteSoftware(String softwareId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Deleting software");
            }
            log.info("Deleting software...");
            softwareDao.deleteSoftwareById(softwareId);
            log.info("Software deleted...");
            return okRespond(null);
        } catch (DataAccessException exception) {
            log.warn("Error deleting software", exception);
            throwBadRequest("Error deleting software", exception);
        } catch (Exception exception) {
            log.error("Critical error deleting software", exception);
            throwInternalServerError("Critical error deleting software", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Software> getSoftware(String softwareId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Getting software");
            }
            log.info("Getting software...");
            Software software = softwareDao.getSoftwareById(softwareId);
            log.info("Software retrieved...");
            return okRespond(software);
        } catch (DataAccessException exception) {
            log.warn("Error getting software", exception);
            throwBadRequest("Error getting software", exception);
        } catch (Exception exception) {
            log.error("Critical error getting software", exception);
            throwInternalServerError("Critical error getting software", exception);
        }
        return null;
    }

    @Override
    public ResponseEntity<Software> updateSoftware(String softwareId, Software body) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Updating software");
            }
            log.info("Updating software...");
            body.setSoftwareId(softwareId);
            softwareDao.updateSoftware(body);
            log.info("Software updated...");
            return okRespond(null);
        } catch (DataAccessException exception) {
            log.warn("Error updating software", exception);
            throwBadRequest("Error updating software", exception);
        } catch (Exception exception) {
            log.error("Critical error updating software", exception);
            throwInternalServerError("Critical error updating software", exception);
        }
        return null;
    }
}
