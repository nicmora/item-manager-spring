package com.nicmora.itemmanagerspring.security.dto;

public record SignUpDTO(
        String username,
        String password,
        String email
) {
}
