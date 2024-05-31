package com.nicmora.itemmanagerspring.handler;

import com.nicmora.itemmanagerspring.domain.dto.ItemDTO;
import com.nicmora.itemmanagerspring.domain.dto.ItemRequestDTO;
import com.nicmora.itemmanagerspring.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemHandler {

    private final ItemService itemService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemService.getAll(), ItemDTO.class);
    }

    public Mono<ServerResponse> getByName(ServerRequest request) {
        String name = request.pathVariable("name");

        return itemService.getByName(name)
                .flatMap(itemDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(itemDTO));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ItemRequestDTO.class)
                .flatMap(itemService::create)
                .flatMap(itemDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(itemDTO));
    }

    public Mono<ServerResponse> updateByName(ServerRequest request) {
        String name = request.pathVariable("name");

        return request.bodyToMono(ItemRequestDTO.class)
                .flatMap(itemRequestDTO -> itemService.updateByName(name, itemRequestDTO))
                .flatMap(itemDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(itemDTO));
    }

    public Mono<ServerResponse> deleteByName(ServerRequest request) {
        String name = request.pathVariable("name");

        return itemService.deleteByName(name)
                .then(Mono.defer(() -> ServerResponse.noContent().build()));
    }

}
