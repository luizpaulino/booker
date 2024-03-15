package com.booker.booking.persistence.entity;

import com.booker.location.dto.response.LocationResponse;
import com.booker.location.persistence.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class LocationBooking {
    private String id;
    private String name;
    private Address address;
    private List<@NotBlank String> facilities;

    private String checkInTime;
    private String checkOutTime;

    public static LocationBooking fromLocationResponse(LocationResponse locationResponse) {
        LocationBooking locationBooking = new LocationBooking();
        locationBooking.setId(locationResponse.getId());
        locationBooking.setName(locationResponse.getName());
        locationBooking.setAddress(locationResponse.getAddress());
        locationBooking.setFacilities(locationResponse.getFacilities());
        locationBooking.setCheckInTime(locationResponse.getCheckInTime());
        locationBooking.setCheckOutTime(locationResponse.getCheckOutTime());
        return locationBooking;
    }
}
