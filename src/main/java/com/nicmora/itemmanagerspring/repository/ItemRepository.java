package com.nicmora.itemmanagerspring.repository;

import com.nicmora.itemmanagerspring.domain.entity.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

    Mono<Item> findByName(String name);
    Mono<Boolean> existsByName(String name);
    Mono<Void> deleteByName(String name);

}
