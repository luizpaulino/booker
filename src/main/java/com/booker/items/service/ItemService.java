package com.booker.items.service;

import org.springframework.stereotype.Service;

import com.booker.items.dto.request.ItemRequest;
import com.booker.items.dto.response.ItemResponse;
import com.booker.items.persistence.entity.Item;
import com.booker.items.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemResponse addItem(ItemRequest itemRequest) {
        itemRepository.findByDescription(itemRequest.getDescription()).ifPresent(location -> {
            throw new IllegalArgumentException("Item with description " + itemRequest.getDescription() + " already exists");
        });
        Item item = itemRepository.save(Item.fromItemRequest(itemRequest));
        return ItemResponse.fromItem(item);
    }

    public ItemResponse getItemById(String itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));
        return ItemResponse.fromItem(item);
    }

    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemResponse::fromItem)
                .collect(Collectors.toList());
    }

    public ItemResponse updateItem(String itemId, ItemRequest itemRequest) {
        itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));
        Item updatedItem = Item.fromItemRequest(itemRequest);
        updatedItem.setId(itemId);
        Item savedItem = itemRepository.save(updatedItem);
        return ItemResponse.fromItem(savedItem);
    }

    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }
}
