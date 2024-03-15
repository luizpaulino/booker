package com.booker.booking.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "bookings")
@Getter
@Setter
public class Booking {
    @Id
    private String id;
    private CustomerBooking customer;
    private LocationBooking locationBooking;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfPeople;
    private List<SelectedRoom> selectedRooms;
    private List<AdditionalItem> additionalItems;
    private BigDecimal totalPrice;

    public Booking(
            CustomerBooking customer,
            LocationBooking locationBooking,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfPeople,
            List<SelectedRoom> selectedRooms,
            List<AdditionalItem> additionalItems,
            BigDecimal totalPrice
    ) {
        this.customer = customer;
        this.locationBooking = locationBooking;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.selectedRooms = selectedRooms;
        this.additionalItems = additionalItems;
        this.totalPrice = totalPrice;
    }
}
