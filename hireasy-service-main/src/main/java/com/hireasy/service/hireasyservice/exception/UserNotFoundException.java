package com.hireasy.service.hireasyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(Exception e) {
        super(e);
    }
    public UserNotFoundException(String message, Exception e) {
        super(message, e);
    }
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
