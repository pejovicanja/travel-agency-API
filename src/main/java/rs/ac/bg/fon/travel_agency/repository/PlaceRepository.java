package rs.ac.bg.fon.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.travel_agency.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {}
