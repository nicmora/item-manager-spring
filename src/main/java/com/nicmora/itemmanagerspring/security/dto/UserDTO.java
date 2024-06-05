package com.nicmora.itemmanagerspring.security.dto;

public record UserDTO(
        String username,
        String email,
        String roles,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled) {
}
