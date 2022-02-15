package com.hireasy.service.hireasyservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {
    String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }
}
