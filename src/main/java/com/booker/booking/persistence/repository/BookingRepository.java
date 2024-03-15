package com.booker.booking.persistence.repository;

import com.booker.booking.persistence.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository  extends MongoRepository<Booking, String> {
    @Query("{$or:[" +
                "{$and:[{checkInDate:{$gte:?0}},{checkInDate:{$lte:?1}}]}," +
                "{$and:[{checkOutDate:{$gte:?0}},{checkOutDate:{$lte:?1}}]}," +
                "{$and:[{checkInDate:{$lte:?0}},{checkOutDate:{$gte:?1}}]}" +
            "]}")
    List<Booking> findBookingsWithinDateRange(LocalDate checkInDate, LocalDate checkOutDate);
}
