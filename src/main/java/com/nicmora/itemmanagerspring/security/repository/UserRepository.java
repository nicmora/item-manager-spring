package com.nicmora.itemmanagerspring.security.repository;

import com.nicmora.itemmanagerspring.security.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);
    Mono<Boolean> existsByUsernameOrEmail(String username, String email);

}
