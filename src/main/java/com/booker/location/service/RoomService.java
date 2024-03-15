package com.booker.location.service;

import com.booker.location.dto.request.RoomRequest;
import com.booker.location.dto.response.RoomResponse;
import com.booker.location.persistence.entity.Building;
import com.booker.location.persistence.entity.Location;
import com.booker.location.persistence.entity.Room;
import com.booker.location.persistence.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private LocationRepository locationRepository;
    public RoomResponse addRoom(RoomRequest roomRequest, String locationId, String buildingId) {

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        Room room = Room.fromRoomRequest(roomRequest);

        building.getRooms().add(room);

        locationRepository.save(location);

        return RoomResponse.fromRoom(room);
    }

    public List<RoomResponse> getAllRooms(String locationId, String buildingId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        return building.getRooms().stream()
                .map(RoomResponse::fromRoom)
                .collect(Collectors.toList());
    }

    public RoomResponse getRoomById(String locationId, String buildingId, String roomId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        Optional<Room> roomOptional = building.getRooms().stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst();

        return roomOptional.map(RoomResponse::fromRoom)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + roomId));
    }

    public RoomResponse updateRoom(RoomRequest roomRequest, String locationId, String buildingId, String roomId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        Room roomToUpdate = building.getRooms().stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + roomId));

        if (!roomRequest.getType().isBlank()) {
            roomToUpdate.setType(roomRequest.getType());
        }

        if (!roomRequest.getBeds().isBlank()) {
            roomToUpdate.setBeds(roomRequest.getBeds());
        }

        if (roomRequest.getCapacity() != null) {
            roomToUpdate.setCapacity(roomRequest.getCapacity());
        }

        if (roomRequest.getOtherFurniture() != null) {
            roomToUpdate.setOtherFurniture(roomRequest.getOtherFurniture());
        }

        locationRepository.save(location);

        return RoomResponse.fromRoom(roomToUpdate);
    }

    public void deleteRoom(String locationId, String buildingId, String roomId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + locationId));

        Building building = location.getBuildings().stream()
                .filter(b -> b.getId().equals(buildingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + buildingId));

        boolean removed = building.getRooms().removeIf(room -> room.getId().equals(roomId));
        if (!removed) {
            throw new IllegalArgumentException("Room not found with id: " + roomId);
        }

        locationRepository.save(location);
    }
}
