package com.booker.items.persistence.entity;


import com.booker.items.dto.request.ItemRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "items")
@Getter
@Setter
public class Item {

    @Id
    private String id;

    private String description;

    private BigDecimal price;

    private String type;

    public static Item fromItemRequest(ItemRequest itemRequest) {
        Item item = new Item();
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setType(itemRequest.getType());
        return item;
    }
}
