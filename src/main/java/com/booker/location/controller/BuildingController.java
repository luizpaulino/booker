package com.booker.location.controller;


import com.booker.location.dto.request.BuildingRequest;
import com.booker.location.dto.response.BuildingResponse;
import com.booker.location.dto.response.BuildingRoomResponse;
import com.booker.location.service.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/locations/{locationId}/buildings")
public class BuildingController {

    private static final Logger logger = Logger.getLogger(LocationController.class.getName());

    @Autowired
    private BuildingService buildingService;

    @PostMapping
    public ResponseEntity<BuildingResponse> createBuilding(@RequestBody @Valid BuildingRequest buildingRequest, @PathVariable String locationId) {
        BuildingResponse buildingResponse = buildingService.addBuilding(buildingRequest, locationId);

        URI buildingUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(buildingResponse.getId())
                .toUri();

        return ResponseEntity.created(buildingUri).body(buildingResponse);
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingResponse> getBuildingById(@PathVariable String locationId, @PathVariable String buildingId) {
        BuildingResponse buildingResponse = buildingService.getBuildingById(locationId, buildingId);
        return ResponseEntity.ok(buildingResponse);
    }

    @GetMapping
    public ResponseEntity<List<BuildingRoomResponse>> listAllBuildingsByLocation(@PathVariable String locationId) {
        List<BuildingRoomResponse> buildingRoomResponse = buildingService.listAllBuildingsByLocation(locationId);
        return ResponseEntity.ok(buildingRoomResponse);
    }

    @PutMapping("/{buildingId}")
    public ResponseEntity<BuildingResponse> updateBuilding(@RequestBody @Valid BuildingRequest buildingRequest,
                                                           @PathVariable String locationId,
                                                           @PathVariable String buildingId) {
        BuildingResponse updatedBuilding = buildingService.updateBuilding(buildingRequest, locationId, buildingId);
        return ResponseEntity.ok(updatedBuilding);
    }

    @DeleteMapping("/{buildingId}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable String locationId, @PathVariable String buildingId) {
        buildingService.deleteBuilding(locationId, buildingId);
        return ResponseEntity.noContent().build();
    }
}
