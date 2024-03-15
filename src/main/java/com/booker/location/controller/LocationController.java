package com.booker.location.controller;

import com.booker.location.dto.request.LocationRequest;
import com.booker.location.dto.response.LocationResponse;
import com.booker.location.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/locations")
public class LocationController {

    private static final Logger logger = Logger.getLogger(LocationController.class.getName());

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationRequest locationRequest) {
        logger.info("Creating location: " + locationRequest.getName());
        LocationResponse locationResponse = locationService.addLocation(locationRequest);

        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(locationResponse.getId())
                .toUri();

        return ResponseEntity.created(locationUri).body(locationResponse);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> listAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable String id) {
        return ResponseEntity.ok(locationService.finById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable String id, @RequestBody LocationRequest locationRequest) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}