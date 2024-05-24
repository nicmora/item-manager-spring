package com.nicmora.itemmanagerspring.mapper;

import com.nicmora.itemmanagerspring.domain.dto.ItemDTO;
import com.nicmora.itemmanagerspring.domain.entity.Item;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ItemMapper implements Function<Item, ItemDTO> {

    @Override
    public ItemDTO apply(Item item) {
        return ItemDTO.builder()
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

}
