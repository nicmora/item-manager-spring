package com.nicmora.itemmanagerspring.exception;

import com.nicmora.itemmanagerspring.handler.ItemHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice(assignableTypes = ItemHandler.class)
public class ItemRouterAdvisor {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public Mono<ResponseEntity<String>> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return Mono.just(ex)
                .map(Throwable::getMessage)
                .map(msg -> ResponseEntity.status(HttpStatus.CONFLICT).body(msg));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<String>> resourceAlreadyExistsException(ResourceNotFoundException ex) {
        return Mono.just(ex)
                .map(Throwable::getMessage)
                .map(msg -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> resourceAlreadyExistsException() {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error"));
    }

}
