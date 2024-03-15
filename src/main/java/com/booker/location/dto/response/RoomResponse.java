package com.booker.location.dto.response;

import com.booker.location.persistence.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    private String id;
    private String type;
    private Integer capacity;
    private String beds;
    private List<String> otherFurniture;
    private BigDecimal dailyPrice;
    private String status;

    public static RoomResponse fromRoom(Room room) {
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setType(room.getType());
        roomResponse.setCapacity(room.getCapacity());
        roomResponse.setBeds(room.getBeds());
        roomResponse.setOtherFurniture(room.getOtherFurniture());
        roomResponse.setDailyPrice(room.getDailyPrice());
        roomResponse.setStatus(room.getStatus());
        return roomResponse;
    }
}
