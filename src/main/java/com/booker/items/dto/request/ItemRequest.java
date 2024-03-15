package com.booker.items.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequest {

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String type;
}
