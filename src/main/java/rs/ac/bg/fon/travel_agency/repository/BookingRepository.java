package rs.ac.bg.fon.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.travel_agency.domain.Booking;
import rs.ac.bg.fon.travel_agency.domain.User;

import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Set<Booking> findBookingByUser(User user);
}
