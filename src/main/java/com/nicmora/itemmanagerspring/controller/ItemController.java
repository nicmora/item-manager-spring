package com.nicmora.itemmanagerspring.controller;

import com.nicmora.itemmanagerspring.domain.dto.ItemDTO;
import com.nicmora.itemmanagerspring.domain.dto.ItemRequestDTO;
import com.nicmora.itemmanagerspring.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Flux<ItemDTO> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/{name}")
    public Mono<ResponseEntity<ItemDTO>> getByName(@PathVariable String name) {
        return itemService.getByName(name)
                .map(itemDTO -> ResponseEntity.ok().body(itemDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ItemDTO>> create(@RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.create(itemRequestDTO)
                .map(itemDTO -> ResponseEntity.status(HttpStatus.CREATED).body(itemDTO));
    }

    @PutMapping("/{name}")
    public Mono<ResponseEntity<ItemDTO>> updateByName(@PathVariable String name,
                                                      @RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.updateByName(name, itemRequestDTO)
                .map(item -> ResponseEntity.ok().body(item));
    }

    @DeleteMapping("/{name}")
    public Mono<ResponseEntity<Void>> deleteByName(@PathVariable String name) {
        return itemService.deleteByName(name)
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

}
