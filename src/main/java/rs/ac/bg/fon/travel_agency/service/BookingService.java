package rs.ac.bg.fon.travel_agency.service;

import rs.ac.bg.fon.travel_agency.domain.Booking;
import rs.ac.bg.fon.travel_agency.domain.User;

import java.util.Set;

public interface BookingService {

    Set<Booking> getBookingsByUser(User user);

    Booking getBookingById(Long id);

    void removeBooking(Booking booking);

    Booking saveBooking(Booking booking);
}
