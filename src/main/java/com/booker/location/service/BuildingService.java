package com.booker.location.service;

import com.booker.location.dto.request.BuildingRequest;
import com.booker.location.dto.response.BuildingResponse;
import com.booker.location.dto.response.BuildingRoomResponse;
import com.booker.location.persistence.entity.Building;
import com.booker.location.persistence.entity.Location;
import com.booker.location.persistence.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    @Autowired
    private LocationRepository locationRepository;
    public BuildingResponse addBuilding(BuildingRequest buildingRequest, String locationId) {

        Location location = locationRepository.findById(locationId).orElseThrow(IllegalArgumentException::new);

        String buildingName = buildingRequest.getName();
        if (location.getBuildings().stream().anyMatch(building -> building.getName().equals(buildingName))) {
            throw new IllegalArgumentException("Building with name '" + buildingName + "' already exists in location.");
        }

        Building building = Building.fromBuildingRequest(buildingRequest);

        location.getBuildings().add(building);

        locationRepository.save(location);

        return BuildingResponse.fromBuilding(building);
    }

    public BuildingResponse getBuildingById(String locationId, String buildingId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        return BuildingResponse.fromBuilding(building);
    }

    public BuildingResponse updateBuilding(BuildingRequest buildingRequest, String locationId, String buildingId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building buildingToUpdate = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        if (!buildingRequest.getName().isBlank()) {
            buildingToUpdate.setName(buildingRequest.getName());
        }

        locationRepository.save(location);

        return BuildingResponse.fromBuilding(buildingToUpdate);
    }

    public void deleteBuilding(String locationId, String buildingId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building buildingToDelete = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        location.getBuildings().remove(buildingToDelete);

        locationRepository.save(location);
    }

    public List<BuildingRoomResponse> listAllBuildingsByLocation(String locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        return location.getBuildings().stream()
                .map(BuildingRoomResponse::fromBuilding)
                .collect(Collectors.toList());
    }

    public List<BuildingRoomResponse> listAllBuildings() {
        List<Location> locations = locationRepository.findAll();

        return locations.stream()
                .flatMap(location -> location.getBuildings().stream())
                .map(BuildingRoomResponse::fromBuilding)
                .toList();
    }
}
