package com.nicmora.itemmanagerspring.router;

import com.nicmora.itemmanagerspring.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ItemRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> itemRouter(ItemHandler itemHandler) {
        return RouterFunctions.route()
                .path("/api/v2/items", () -> RouterFunctions.route()
                        .GET("", itemHandler::getAll)
                        .GET("/{name}", itemHandler::getByName)
                        .POST("", itemHandler::create)
                        .PUT("/{name}", itemHandler::updateByName)
                        .DELETE("/{name}", itemHandler::deleteByName)
                        .build())
                .build();
    }

}
