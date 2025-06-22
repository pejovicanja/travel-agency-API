package rs.ac.bg.fon.travel_agency.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.bg.fon.travel_agency.domain.Place;
import rs.ac.bg.fon.travel_agency.exception.custom.NotFoundException;
import rs.ac.bg.fon.travel_agency.repository.PlaceRepository;
import rs.ac.bg.fon.travel_agency.service.PlaceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Place getPlaceById(Long id) {
        return placeRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Place with ID: " + id + " can't be found"));
    }

    @Override
    @Transactional
    public Place saveOrUpdate(Place place) {
        return placeRepository.save(place);
    }
}
