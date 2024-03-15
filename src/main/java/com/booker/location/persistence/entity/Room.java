package com.booker.location.persistence.entity;

import java.math.BigDecimal;
import java.util.List;


import com.booker.location.dto.request.RoomRequest;
import com.booker.location.model.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    private String id = new ObjectId().toHexString();
    private String type;
    private Integer capacity;
    private String beds;
    private BigDecimal dailyPrice;
    private List<String> otherFurniture;
    private String status = RoomStatus.available.toString();

    public static Room fromRoomRequest(RoomRequest roomRequest) {
        Room room = new Room();
        room.setType(roomRequest.getType());
        room.setCapacity(roomRequest.getCapacity());
        room.setBeds(roomRequest.getBeds());
        room.setDailyPrice(roomRequest.getDailyPrice());
        room.setOtherFurniture(roomRequest.getOtherFurniture());
        return room;
    }
}
