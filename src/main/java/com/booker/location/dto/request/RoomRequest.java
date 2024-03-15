package com.booker.location.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomRequest {

    @NotBlank
    private String type;

    @NotNull
    private Integer capacity;

    @NotBlank
    private String beds;

    @NotNull
    private BigDecimal dailyPrice;

    @NotNull
    private List<@NotBlank String> otherFurniture;
}
