package com.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommitNotFoundException extends RuntimeException{
    public CommitNotFoundException(String message) {
        super(message);
    }
}
