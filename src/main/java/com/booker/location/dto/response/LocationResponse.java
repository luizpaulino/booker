package com.booker.location.dto.response;

import com.booker.location.persistence.entity.Address;
import com.booker.location.persistence.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationResponse {

    private String id;
    private String name;
    private Address address;
    private List<String> facilities;
    private String checkInTime;
    private String checkOutTime;

    public static LocationResponse fromLocation(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getFacilities(),
                location.getCheckInTime(),
                location.getCheckOutTime());

    }
}
