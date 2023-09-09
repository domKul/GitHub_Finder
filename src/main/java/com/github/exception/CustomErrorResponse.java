package com.github.exception;

public class CustomErrorResponse {
    private final int status;
    private final String message;

    public CustomErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
