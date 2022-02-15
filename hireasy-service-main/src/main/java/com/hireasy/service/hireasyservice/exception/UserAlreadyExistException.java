package com.hireasy.service.hireasyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UserAlreadyExistException() {
        super();
    }
    public UserAlreadyExistException(Exception e) {
        super(e);
    }
    public UserAlreadyExistException(String message, Exception e) {
        super(message, e);
    }
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
