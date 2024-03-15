package com.booker.location.persistence.entity;

import com.booker.location.dto.request.BuildingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Building {

    private String id = new ObjectId().toHexString();

    private String name;

    private List<Room> rooms = List.of();

    public static Building fromBuildingRequest(BuildingRequest buildingRequest) {
        Building building = new Building();
        building.setName(buildingRequest.getName());
        return building;
    }
}
