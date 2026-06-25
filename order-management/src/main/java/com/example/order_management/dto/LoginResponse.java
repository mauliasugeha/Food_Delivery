package com.example.order_management.dto;

public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}