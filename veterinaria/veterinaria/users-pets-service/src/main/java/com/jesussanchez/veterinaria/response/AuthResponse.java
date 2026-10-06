package com.jesussanchez.veterinaria.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {

    }
}