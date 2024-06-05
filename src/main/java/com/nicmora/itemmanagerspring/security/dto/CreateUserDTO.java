package com.nicmora.itemmanagerspring.security.dto;

public record CreateUserDTO(
        String username,
        String password,
        String email
) {
}
