package com.booker.items.persistence.repository;

import com.booker.items.persistence.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findByDescription(String description);
}
