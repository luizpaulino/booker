package com.booker.booking.controller;

import com.booker.booking.dto.request.BookingRequest;
import com.booker.booking.dto.response.BookingResponse;
import com.booker.booking.service.BookingService;
import com.booker.customer.controller.CustomerController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBook(@RequestBody @Valid BookingRequest bookingRequest) {
        BookingResponse bookingResponse = bookingService.addBook(bookingRequest);
        URI bookingUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookingResponse.getId())
                .toUri();
        return ResponseEntity.created(bookingUri).body(bookingResponse);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookingResponses = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse bookingResponse = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
