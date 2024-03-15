package com.booker.location.dto.response;

import com.booker.location.persistence.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildingResponse {

    private String id;
    private String name;

    public static BuildingResponse fromBuilding(Building building) {
        return new BuildingResponse(
                building.getId(),
                building.getName()
        );
    }
}
