package com.booker.booking.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class SelectedRoom {

    private String buildingId;
    private String roomId;
    private String type;
    private Integer capacity;
    private String beds;
    private BigDecimal dailyPrice;
}
