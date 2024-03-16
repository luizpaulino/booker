package com.booker.location.controller;

import com.booker.location.dto.request.RoomRequest;
import com.booker.location.dto.response.RoomResponse;
import com.booker.location.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/locations/{locationId}/buildings/{buildingId}/rooms")
public class RoomController {

    private static final Logger logger = Logger.getLogger(RoomController.class.getName());

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestBody @Valid RoomRequest roomRequest,
            @PathVariable String locationId,
            @PathVariable String buildingId
    ) {
        RoomResponse roomResponse = roomService.addRoom(roomRequest, locationId, buildingId);

        URI roomUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roomResponse.getId())
                .toUri();

        return ResponseEntity.created(roomUri).body(roomResponse);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable String locationId,
            @PathVariable String buildingId,
            @PathVariable String roomId
    ) {
        RoomResponse roomResponse = roomService.getRoomById(locationId, buildingId, roomId);
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms(
            @PathVariable String locationId,
            @PathVariable String buildingId
    ) {
        List<RoomResponse> roomResponses = roomService.getAllRooms(locationId, buildingId);
        return ResponseEntity.ok(roomResponses);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(
            @RequestBody @Valid RoomRequest roomRequest,
            @PathVariable String locationId,
            @PathVariable String buildingId,
            @PathVariable String roomId
    ) {
        RoomResponse updatedRoom = roomService.updateRoom(roomRequest, locationId, buildingId, roomId);
        return ResponseEntity.ok(updatedRoom);
    }

    @PostMapping("/{roomId}/status")
    public ResponseEntity<Void> blockRoom(
            @PathVariable String locationId,
            @PathVariable String buildingId,
            @PathVariable String roomId
    ) {
        roomService.blockRoom(locationId, buildingId, roomId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{roomId}/status")
    public ResponseEntity<Void> unblockRoom(
            @PathVariable String locationId,
            @PathVariable String buildingId,
            @PathVariable String roomId
    ) {
        roomService.unblockRoom(locationId, buildingId, roomId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable String locationId,
            @PathVariable String buildingId,
            @PathVariable String roomId
    ) {
        roomService.deleteRoom(locationId, buildingId, roomId);
        return ResponseEntity.noContent().build();
    }

}