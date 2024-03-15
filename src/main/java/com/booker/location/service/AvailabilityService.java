package com.booker.location.service;

import com.booker.booking.persistence.entity.Booking;
import com.booker.booking.persistence.entity.SelectedRoom;
import com.booker.booking.persistence.repository.BookingRepository;
import com.booker.location.persistence.entity.Building;
import com.booker.location.persistence.entity.Location;
import com.booker.location.persistence.entity.Room;
import com.booker.location.service.BuildingService;
import com.booker.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private LocationService locationService;

    public List<Location> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> bookingsWithinDates =
                bookingRepository.findBookingsWithinDateRange(
                        checkInDate,
                        checkOutDate
                );

        List<Location> allLocations = locationService.findAllLocationsAvailable();

        List<String> bookedRoomIds = bookingsWithinDates.stream()
                .flatMap(booking -> booking.getSelectedRooms().stream())
                .map(SelectedRoom::getRoomId)
                .toList();

        for (Location location : allLocations) {
            for (Building building : location.getBuildings()) {
                List<Room> availableRooms = building.getRooms().stream()
                        .filter(room -> !bookedRoomIds.contains(room.getId()))
                        .toList();
                building.setRooms(availableRooms);
            }
        }

        return allLocations;
    }

    public boolean validateRoomsAvailability(List<SelectedRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Location> availableLocations = findAvailableRooms(checkInDate, checkOutDate);
        List<String> availableRoomIds = availableLocations.stream()
                .flatMap(location -> location.getBuildings().stream())
                .flatMap(building -> building.getRooms().stream())
                .map(Room::getId)
                .toList();

        for (SelectedRoom selectedRoom : rooms) {
            if (!availableRoomIds.contains(selectedRoom.getRoomId())) {
                return false;
            }
        }
        return true;
    }

}
