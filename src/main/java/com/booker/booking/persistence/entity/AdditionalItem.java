package com.booker.booking.persistence.entity;

import com.booker.items.dto.response.ItemResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class AdditionalItem {

    private String id;
    private String description;
    private BigDecimal price;
    private String type;
    private Integer quantity;

    public static AdditionalItem fromItemResponse(ItemResponse itemResponse) {
        AdditionalItem additionalItem = new AdditionalItem();
        additionalItem.setId(itemResponse.getId());
        additionalItem.setDescription(itemResponse.getDescription());
        additionalItem.setPrice(itemResponse.getPrice());
        additionalItem.setType(itemResponse.getType());
        additionalItem.setQuantity(itemResponse.getQuantity());
        return additionalItem;
    }
}