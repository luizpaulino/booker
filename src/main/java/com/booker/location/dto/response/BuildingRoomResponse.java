package com.booker.location.dto.response;

import com.booker.location.persistence.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BuildingRoomResponse {

    private String id;
    private String name;

    private List<RoomResponse> rooms;

    public static BuildingRoomResponse fromBuilding(Building building) {
        return new BuildingRoomResponse(
                building.getId(),
                building.getName(),
                building.getRooms().stream()
                        .map(RoomResponse::fromRoom)
                        .toList()
        );
    }

    public static BuildingRoomResponse fromRoomResponse(List<RoomResponse> roomResponse, String buildingId, String buildingName) {
        return new BuildingRoomResponse(
                buildingId,
                buildingName,
                roomResponse
        );
    }


}
