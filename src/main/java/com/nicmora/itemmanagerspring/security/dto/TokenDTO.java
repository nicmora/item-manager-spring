package com.nicmora.itemmanagerspring.security.dto;

public record TokenDTO(
        String token
) {
    public TokenDTO(String token) {
        this.token = token;
    }
}
