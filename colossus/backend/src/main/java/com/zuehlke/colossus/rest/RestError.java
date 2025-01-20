package com.zuehlke.colossus.rest;

public class RestError {
    private String status;
    private String message;

    public RestError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }
}

