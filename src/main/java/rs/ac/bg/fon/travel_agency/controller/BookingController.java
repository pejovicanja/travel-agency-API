package rs.ac.bg.fon.travel_agency.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.travel_agency.domain.Booking;
import rs.ac.bg.fon.travel_agency.domain.Place;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.dto.BookingDto;
import rs.ac.bg.fon.travel_agency.mapper.BookingMapper;
import rs.ac.bg.fon.travel_agency.service.BookingService;
import rs.ac.bg.fon.travel_agency.service.PlaceService;
import rs.ac.bg.fon.travel_agency.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final UserService userService;
    private final BookingService bookingService;
    private final PlaceService placeService;

    public BookingController(
            UserService userService, BookingService bookingService, PlaceService placeService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.placeService = placeService;
    }

    @GetMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<Set<BookingDto>> getBookingsByUser(@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        Set<Booking> bookings = bookingService.getBookingsByUser(user);
        return ResponseEntity.ok(
                bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public void deleteBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        bookingService.removeBooking(booking);
    }

    @PostMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        User user = userService.getUserById(bookingDto.user().id());
        Place place = placeService.getPlaceById(bookingDto.place().id());

        Booking booking = BookingMapper.toEntity(bookingDto);
        booking.setUser(user);
        booking.setPlace(place);

        booking = bookingService.saveBooking(booking);
        return new ResponseEntity<>(BookingMapper.toDto(booking), HttpStatus.OK);
    }
}
