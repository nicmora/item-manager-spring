package com.nicmora.itemmanagerspring.security.controller;

import com.nicmora.itemmanagerspring.security.dto.SignUpDTO;
import com.nicmora.itemmanagerspring.security.dto.LoginDTO;
import com.nicmora.itemmanagerspring.security.dto.TokenDTO;
import com.nicmora.itemmanagerspring.security.dto.UserDTO;
import com.nicmora.itemmanagerspring.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public Mono<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/signup")
    public Mono<UserDTO> create(@RequestBody SignUpDTO signUpDTO) {
        return userService.signup(signUpDTO);
    }

}
