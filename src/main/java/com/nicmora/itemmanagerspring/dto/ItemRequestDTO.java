package com.nicmora.itemmanagerspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ItemRequestDTO {

    @NotBlank
    private String name;
    @PositiveOrZero
    private Integer price;

}

