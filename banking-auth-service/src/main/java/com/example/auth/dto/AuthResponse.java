package com.example.auth.dto;

public class AuthResponse {

    private String token;
    private String message;
    private String accountNumber;

    public AuthResponse() {
    }

    public AuthResponse(String token, String message, String accountNumber) {
        this.token = token;
        this.message = message;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}