package com.github.exception;

public class ApiErrorRespons {
    private String message;
    private int status;

    public ApiErrorRespons(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
