package com.booker.location.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class LocationRequest {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private AddressRequest address;

    @NotNull
    private List<@NotBlank String> facilities;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid check-in time format. Use HH:mm format.")
    private String checkInTime;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid check-out time format. Use HH:mm format.")
    private String checkOutTime;
}
