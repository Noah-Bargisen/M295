package com.ubs.m295_projectapplication.controller;

import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public abstract class AbstractController {

    protected <T> ResponseEntity<T> okRespond(T result) {
        return ResponseEntity.ok(result);
    }

    protected ResponseStatusException badRequestRespond(Exception e) {
        return new ResponseStatusException(BAD_REQUEST, e.getMessage());
    }

    protected ResponseStatusException internalServerErrorRespond(Exception e) {
        return new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
