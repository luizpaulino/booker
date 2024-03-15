package com.booker.booking.service;

import com.booker.location.service.AvailabilityService;
import com.booker.booking.dto.request.BookingRequest;
import com.booker.booking.dto.response.BookingResponse;
import com.booker.booking.persistence.entity.*;
import com.booker.booking.persistence.repository.BookingRepository;
import com.booker.customer.service.CustomerService;
import com.booker.items.dto.response.ItemResponse;
import com.booker.items.service.ItemService;
import com.booker.location.dto.response.RoomResponse;
import com.booker.location.service.LocationService;
import com.booker.location.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private EmailService emailService;

    public BookingResponse addBook(BookingRequest bookingRequest) {
        CustomerBooking customerBooking =
                CustomerBooking.fromCustomerResponse(customerService.getCustomerById(bookingRequest.getCustomerId()));

        LocationBooking locationBooking =
                LocationBooking.fromLocationResponse(locationService.finById(bookingRequest.getLocationId()));

        List<SelectedRoom> selectedRooms = getSelectedRooms(bookingRequest);

        if (!availabilityService.validateRoomsAvailability(
                selectedRooms,
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        )) {
            throw new IllegalArgumentException("Selected rooms are not available for the given dates");
        }

        int totalDays = bookingRequest.getCheckOutDate().compareTo(bookingRequest.getCheckInDate());

        BigDecimal totalRoomPrice = selectedRooms.stream()
                .map(room -> room.getDailyPrice().multiply(BigDecimal.valueOf(totalDays)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<AdditionalItem> additionalItems = getAdditionalItems(bookingRequest);

        BigDecimal totalItemPrice = additionalItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Booking booking = bookingRepository.save(
            new Booking(
                customerBooking,
                locationBooking,
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getNumberOfPeople(),
                selectedRooms,
                additionalItems,
                totalRoomPrice.add(totalItemPrice)
            )
        );

        String to = customerBooking.getEmail();
        String subject = "Booker - Booking Confirmation";
        String body = emailService.generateEmail(
                customerBooking.getFullName(),
                booking.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getNumberOfPeople(),
                totalRoomPrice.add(totalItemPrice)
        );

        emailService.sendEmail(to, subject, body);

        return BookingResponse.fromBooking(booking);
    }
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));
        return BookingResponse.fromBooking(booking);
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(BookingResponse::fromBooking)
                .toList();
    }


    private List<AdditionalItem> getAdditionalItems(BookingRequest bookingRequest) {
        List<ItemResponse> items = bookingRequest.getAdditionalItems().stream()
                .map(item -> {
                    ItemResponse itemRecovered = itemService.getItemById(item.getId());
                    itemRecovered.setPrice(itemRecovered.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    itemRecovered.setQuantity(item.getQuantity());
                    return itemRecovered;
                })
                .toList();

        return items.stream()
                .map(AdditionalItem::fromItemResponse)
                .toList();
    }

    private List<SelectedRoom> getSelectedRooms(BookingRequest bookingRequest) {
        List<RoomResponse> rooms = bookingRequest.getSelectedRooms().stream()
                .map(room -> roomService.getRoomById(bookingRequest.getLocationId(), room.getBuildingId(), room.getRoomId()))
                .toList();

        int totalCapacity = rooms.stream()
                .mapToInt(RoomResponse::getCapacity)
                .sum();

        if (totalCapacity < bookingRequest.getNumberOfPeople()) {
            throw new IllegalArgumentException("Total capacity of selected rooms is less than number of people");
        }

        return rooms.stream()
                .map(room -> convertToSelectedRoom(room, bookingRequest.getLocationId()))
                .toList();
    }

    private static SelectedRoom convertToSelectedRoom(RoomResponse roomResponse, String buildingId) {
        SelectedRoom selectedRoom = new SelectedRoom();
        selectedRoom.setBuildingId(buildingId);
        selectedRoom.setRoomId(roomResponse.getId());
        selectedRoom.setType(roomResponse.getType());
        selectedRoom.setCapacity(roomResponse.getCapacity());
        selectedRoom.setBeds(roomResponse.getBeds());
        selectedRoom.setDailyPrice(roomResponse.getDailyPrice());

        return selectedRoom;
    }
}
