package com.ubs.m295_projectapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public abstract class AbstractController {

    protected static <T> ResponseEntity<T> okRespond(T result) {
        return ResponseEntity.ok(result);
    }

    protected static void throwBadRequest(String message, Throwable cause) {
        throw new ResponseStatusException(BAD_REQUEST, message, cause);
    }

    protected static void throwInternalServerError(String message, Throwable cause) {
        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, message, cause);
    }
}
