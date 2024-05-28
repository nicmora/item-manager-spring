package com.nicmora.itemmanagerspring.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("items")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Integer price;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

}
