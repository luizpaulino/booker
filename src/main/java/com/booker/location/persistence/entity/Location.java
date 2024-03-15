package com.booker.location.persistence.entity;

import com.booker.location.dto.request.LocationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "locations")
@Getter
@Setter
public class Location {
    @Id
    private String id;
    private String name;
    private Address address;
    private List<@NotBlank String> facilities;

    private String checkInTime;
    private String checkOutTime;

    private List<Building> buildings = List.of();

    public static Location fromLocationRequest(LocationRequest locationRequest) {
        Location location = new Location();
        location.setName(locationRequest.getName());
        location.setAddress(Address.fromAddressRequest(locationRequest.getAddress()));
        location.setFacilities(locationRequest.getFacilities());
        location.setCheckInTime(locationRequest.getCheckInTime());
        location.setCheckOutTime(locationRequest.getCheckOutTime());
        return location;
    }
}
