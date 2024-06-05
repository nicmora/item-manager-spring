package com.nicmora.itemmanagerspring.security.jwt;

import com.nicmora.itemmanagerspring.security.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getPath().value();
        if (path.contains("auth") || path.contains("health")) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return Mono.error(new BadRequestException("no token was found"));
        }

        if (!auth.startsWith("Bearer ")) {
            return Mono.error(new BadRequestException("invalid auth"));
        }

        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);

        return chain.filter(exchange);
    }

}
