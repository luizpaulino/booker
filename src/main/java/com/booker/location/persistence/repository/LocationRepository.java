package com.booker.location.persistence.repository;

import com.booker.location.persistence.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

    Optional<Location> findByName(String name);
}
