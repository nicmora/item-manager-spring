package com.nicmora.itemmanagerspring.security.mapper;

import com.nicmora.itemmanagerspring.security.dto.UserDTO;
import com.nicmora.itemmanagerspring.security.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }

}
