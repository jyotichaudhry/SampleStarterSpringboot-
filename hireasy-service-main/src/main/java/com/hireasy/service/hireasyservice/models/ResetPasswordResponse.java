package com.hireasy.service.hireasyservice.models;

public class ResetPasswordResponse {
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public ResetPasswordResponse() {
    }

    public ResetPasswordResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
