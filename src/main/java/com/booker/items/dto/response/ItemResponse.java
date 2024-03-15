package com.booker.items.dto.response;

import com.booker.items.persistence.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemResponse {

    private String id;
    private String description;
    private BigDecimal price;
    private String type;
    @JsonIgnore
    private Integer quantity;

    public static ItemResponse fromItem(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setDescription(item.getDescription());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setType(item.getType());
        return itemResponse;
    }
}
