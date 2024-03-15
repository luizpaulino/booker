package com.booker.items.controller;

import com.booker.items.dto.request.ItemRequest;
import com.booker.items.dto.response.ItemResponse;
import com.booker.items.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/items")
public class ItemsController {

    private static final Logger logger = Logger.getLogger(ItemsController.class.getName());

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.addItem(itemRequest);

        URI itemUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemResponse.getId())
                .toUri();

        return ResponseEntity.created(itemUri).body(itemResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable String id) {
        ItemResponse itemResponse = itemService.getItemById(id);
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> itemResponses = itemService.getAllItems();
        return ResponseEntity.ok(itemResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@RequestBody @Valid ItemRequest itemRequest, @PathVariable String id) {
        ItemResponse updatedItem = itemService.updateItem(id, itemRequest);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
