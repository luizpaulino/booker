package com.booker.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    @NotBlank
    private String country;

    @NotBlank
    private String documentNumber;

    @NotBlank
    private String fullName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "\\+\\d{2}\\s\\d{2}\\s\\d{5}-\\d{4}")
    private String phone;

    @NotBlank
    @Email
    private String email;

    // Getters and setters
}
