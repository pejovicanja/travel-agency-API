package rs.ac.bg.fon.travel_agency.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.bg.fon.travel_agency.domain.Place;

import java.util.List;

public interface PlaceService {

    List<Place> getPlaces(Pageable pageable);

    Place getPlaceById(Long id);

    Place saveOrUpdate(Place place);
}
