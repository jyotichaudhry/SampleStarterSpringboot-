package com.hireasy.service.hireasyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public InvalidCredentialsException() {
        super();
    }
    public InvalidCredentialsException(Exception e) {
        super(e);
    }
    public InvalidCredentialsException(String message, Exception e) {
        super(message, e);
    }
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
