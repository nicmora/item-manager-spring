package com.nicmora.itemmanagerspring.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ItemRequestDTO {

    @NotBlank
    private String name;
    @PositiveOrZero
    private Integer price;

}
