package com.ubs.m295_projectapplication.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RequestCreator {

    public static HttpEntity<Object> GET_REQUEST(String body, boolean isAuth) {

        return new HttpEntity<Object>(body, GET_HEADER(isAuth));
    }

    private static HttpHeaders GET_HEADER(boolean isAuth) {
        if (isAuth) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("user", "password");
            headers.setConnection("keep-alive");
            return headers;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setConnection("keep-alive");
            return headers;
        }
    }
}
