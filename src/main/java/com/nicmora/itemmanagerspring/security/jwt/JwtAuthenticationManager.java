package com.nicmora.itemmanagerspring.security.jwt;

import com.nicmora.itemmanagerspring.security.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class implements the ReactiveAuthenticationManager interface, integrating with Spring Security and working together with the JWT provider (JwtProvider).
 * Its main functions include authenticating the credentials provided in a JWT token, retrieving the claims associated with the token and mapping them to authority roles,
 * as well as handling potential errors, such as an invalid token.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(new UnauthorizedException("bad token")))
                .map(claims -> new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                        Stream.of(claims.get("roles"))
                                .map(role -> (List<Map<String, String>>) role)
                                .flatMap(role -> role.stream()
                                        .map(r -> r.get("authority"))
                                        .map(SimpleGrantedAuthority::new))
                                .toList())
                );
    }

}
