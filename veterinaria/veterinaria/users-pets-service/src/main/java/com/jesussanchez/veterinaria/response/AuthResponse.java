package com.jesussanchez.veterinaria.response;

public class AuthResponse {

    private String token;

    public AuthResponse() {
    }

    // Constructor con el token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter y Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}