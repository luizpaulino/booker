package com.booker.location.service;

import com.booker.location.dto.request.AddressRequest;
import com.booker.location.dto.request.LocationRequest;
import com.booker.location.dto.response.LocationResponse;
import com.booker.location.persistence.entity.Address;
import com.booker.location.persistence.entity.Location;
import com.booker.location.persistence.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationResponse addLocation(LocationRequest locationRequest) {

        locationRepository.findByName(locationRequest.getName()).ifPresent(location -> {
            throw new IllegalArgumentException("Location with name " + locationRequest.getName() + " already exists");
        });

        Location location = locationRepository.save(Location.fromLocationRequest(locationRequest));

        return LocationResponse.fromLocation(location);
    }

    public List<LocationResponse> findAll() {
        return locationRepository.findAll().stream().map(LocationResponse::fromLocation).toList();
    }

    public LocationResponse finById(String id) {
        return locationRepository.findById(id).map(LocationResponse::fromLocation).orElseThrow();
    }

    public LocationResponse updateLocation(String id, LocationRequest locationRequest) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));

        Location locationUpdated = locationRepository.save(updateLocationData(location, locationRequest));
        return LocationResponse.fromLocation(locationUpdated);
    }

    public List<Location> findAllLocationsAvailable() {
        return locationRepository.findAll();
    }

    private Location updateLocationData(Location location, LocationRequest locationRequest) {
        updateLocationName(location, locationRequest.getName());
        updateLocationAddress(location, locationRequest.getAddress());
        updateCheckInTime(location, locationRequest.getCheckInTime());
        updateCheckOutTime(location, locationRequest.getCheckOutTime());
        updateFacilities(location, locationRequest.getFacilities());
        return location;
    }

    private void updateLocationName(Location location, String name) {
        if (!name.isBlank()) {
            location.setName(name);
        }
    }

    private void updateLocationAddress(Location location, AddressRequest addressRequest) {
        if (addressRequest != null) {
            updateCity(location.getAddress(), addressRequest.getCity());
            updateZip(location.getAddress(), addressRequest.getZip());
            updateState(location.getAddress(), addressRequest.getState());
            updateStreet(location.getAddress(), addressRequest.getStreet());
        }
    }

    private void updateCity(Address address, String city) {
        if (!city.isBlank()) {
            address.setCity(city);
        }
    }

    private void updateZip(Address address, String zip) {
        if (!zip.isBlank()) {
            address.setZip(zip);
        }
    }

    private void updateState(Address address, String state) {
        if (!state.isBlank()) {
            address.setState(state);
        }
    }

    private void updateStreet(Address address, String street) {
        if (!street.isBlank()) {
            address.setStreet(street);
        }
    }

    private void updateCheckInTime(Location location, String checkInTime) {
        if (!checkInTime.isBlank()) {
            location.setCheckInTime(checkInTime);
        }
    }

    private void updateCheckOutTime(Location location, String checkOutTime) {
        if (!checkOutTime.isBlank()) {
            location.setCheckOutTime(checkOutTime);
        }
    }

    private void updateFacilities(Location location, List<String> facilities) {
        if (!facilities.isEmpty()) {
            location.setFacilities(facilities);
        }
    }

    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }
}
