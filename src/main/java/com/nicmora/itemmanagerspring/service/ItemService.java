package com.nicmora.itemmanagerspring.service;

import com.nicmora.itemmanagerspring.domain.dto.ItemDTO;
import com.nicmora.itemmanagerspring.domain.dto.ItemRequestDTO;
import com.nicmora.itemmanagerspring.domain.entity.Item;
import com.nicmora.itemmanagerspring.exception.ResourceAlreadyExistsException;
import com.nicmora.itemmanagerspring.exception.ResourceNotFoundException;
import com.nicmora.itemmanagerspring.mapper.ItemMapper;
import com.nicmora.itemmanagerspring.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Flux<ItemDTO> getAll() {
        return itemRepository.findAll()
                .map(itemMapper);
    }

    public Mono<ItemDTO> getByName(String name) {
        return itemRepository.findByName(name)
                .map(itemMapper)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceNotFoundException("Item not found"))));
    }

    public Mono<ItemDTO> create(ItemRequestDTO itemRequestDTO) {
        return itemRepository.existsByName(itemRequestDTO.getName())
                .filter(exists -> !exists)
                .map(notExists -> new Item(itemRequestDTO.getName(), itemRequestDTO.getPrice()))
                .flatMap(itemRepository::save)
                .map(itemMapper)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceAlreadyExistsException("Item already exists"))));
    }


    public Mono<ItemDTO> updateByName(String name, ItemRequestDTO itemRequestDTO) {
        return itemRepository.findByName(name)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceNotFoundException("Item not found"))))
                .map(item -> item.toBuilder()
                        .name(itemRequestDTO.getName())
                        .price(itemRequestDTO.getPrice())
                        .build())
                .flatMap(itemRepository::save)
                .map(itemMapper);
    }

    public Mono<Void> deleteByName(String name) {
        return itemRepository.findByName(name)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Item not found")))
                .flatMap(itemRepository::delete);
    }

}
