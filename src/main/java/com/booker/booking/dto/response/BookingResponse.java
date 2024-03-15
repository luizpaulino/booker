package com.booker.booking.dto.response;

import com.booker.booking.persistence.entity.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookingResponse {
    private String id;
    private LocationBooking locationBooking;
    private CustomerBooking customer;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfPeople;
    private List<SelectedRoom> selectedRooms;
    private List<AdditionalItem> additionalItems;
    private BigDecimal totalPrice;


    public static BookingResponse fromBooking(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setLocationBooking(booking.getLocationBooking());
        bookingResponse.setCustomer(booking.getCustomer());
        bookingResponse.setCheckInDate(booking.getCheckInDate());
        bookingResponse.setCheckOutDate(booking.getCheckOutDate());
        bookingResponse.setNumberOfPeople(booking.getNumberOfPeople());
        bookingResponse.setSelectedRooms(booking.getSelectedRooms());
        bookingResponse.setAdditionalItems(booking.getAdditionalItems());
        bookingResponse.setTotalPrice(booking.getTotalPrice());

        return bookingResponse;
    }
}
