package com.booker.booking.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
public class BookingRequest {

    @NotBlank
    private String customerId;

    @NotBlank
    private String locationId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;

    @NotNull
    private int numberOfPeople;

    @NotNull
    private List<RoomSelection> selectedRooms;

    private List<AdditionalItem> additionalItems;

    @Data
    public static class RoomSelection {
        @NotBlank
        private String buildingId;

        @NotBlank
        private String roomId;
    }

    @Data
    public static class AdditionalItem {
        @NotBlank
        private String id;

        @NotNull
        private int quantity;
    }
}

