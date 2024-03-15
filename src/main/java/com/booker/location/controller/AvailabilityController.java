package com.booker.location.controller;

import com.booker.location.service.AvailabilityService;
import com.booker.location.persistence.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/locations/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<List<Location>> getAvailability(
            @RequestParam("checkInDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate) {

        return ResponseEntity.ok(availabilityService.findAvailableRooms(checkInDate, checkOutDate));
    }

}
