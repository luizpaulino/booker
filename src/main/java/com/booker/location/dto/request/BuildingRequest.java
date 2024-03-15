package com.booker.location.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BuildingRequest {

    @NotBlank
    private String name;
}
