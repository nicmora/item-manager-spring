package com.nicmora.itemmanagerspring.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ItemDTO {

    private String name;
    private Integer price;

}
