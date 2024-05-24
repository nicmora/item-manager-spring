package com.nicmora.itemmanagerspring.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemDTO {

    private String name;
    private Integer price;

}
