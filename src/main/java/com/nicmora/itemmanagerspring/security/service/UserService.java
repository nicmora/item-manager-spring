package com.nicmora.itemmanagerspring.security.service;

import com.nicmora.itemmanagerspring.security.dto.SignUpDTO;
import com.nicmora.itemmanagerspring.security.dto.LoginDTO;
import com.nicmora.itemmanagerspring.security.dto.TokenDTO;
import com.nicmora.itemmanagerspring.security.dto.UserDTO;
import com.nicmora.itemmanagerspring.security.entity.User;
import com.nicmora.itemmanagerspring.security.entity.Role;
import com.nicmora.itemmanagerspring.security.exception.BadRequestException;
import com.nicmora.itemmanagerspring.security.jwt.JwtProvider;
import com.nicmora.itemmanagerspring.security.mapper.UserMapper;
import com.nicmora.itemmanagerspring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Mono<TokenDTO> login(LoginDTO loginDTO) {
        return userRepository.findByUsername(loginDTO.username())
                .filter(user -> passwordEncoder.matches(loginDTO.password(), user.getPassword()))
                .map(jwtProvider::generateToken)
                .map(TokenDTO::new)
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }

    public Mono<UserDTO> signup(SignUpDTO signUpDTO) {
        return userRepository.existsByUsernameOrEmail(signUpDTO.username(), signUpDTO.email())
                .filter(exists -> !exists)
                .map(notExists -> User.builder()
                        .username(signUpDTO.username())
                        .password(passwordEncoder.encode(signUpDTO.password()))
                        .email(signUpDTO.email())
                        .roles(Role.ROLE_USER.name())
                        .build())
                .flatMap(userRepository::save)
                .map(userMapper)
                .switchIfEmpty(Mono.error(new BadRequestException("Username already in use")));
    }

}
