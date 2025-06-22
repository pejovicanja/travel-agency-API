package rs.ac.bg.fon.travel_agency.controller;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.travel_agency.domain.Place;
import rs.ac.bg.fon.travel_agency.domain.PlaceLocation;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.dto.PlaceDto;
import rs.ac.bg.fon.travel_agency.mapper.PlaceLocationMapper;
import rs.ac.bg.fon.travel_agency.mapper.PlaceMapper;
import rs.ac.bg.fon.travel_agency.service.PlaceService;
import rs.ac.bg.fon.travel_agency.service.UserService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;
    private final UserService userService;

    public PlaceController(
            PlaceService placeService,
            UserService userService) {
        this.placeService = placeService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<List<PlaceDto>> getPlaces(Pageable pageable) {
        List<Place> places = placeService.getPlaces(pageable);
        return ResponseEntity.ok(
                places.stream().map(PlaceMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<PlaceDto> getPlace(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        Resource placeImage;
        PlaceDto placeDto = PlaceMapper.toDto(place);

        return ResponseEntity.ok(placeDto);
    }

    @PostMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_PRO + "\")")
    public ResponseEntity<Long> createPlace(@RequestBody PlaceDto placeDto) {
        User user = userService.getUserById(placeDto.user().id());
        PlaceLocation placeLocation = PlaceLocationMapper.toEntity(placeDto.placeLocation());

        Place place = PlaceMapper.toEntity(placeDto);
        place.setUser(user);
        place.setPlaceLocation(placeLocation);

        return new ResponseEntity<>(placeService.saveOrUpdate(place).getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_PRO + "\")")
    public ResponseEntity<PlaceDto> updatePlace(
            @PathVariable Long id, @Valid @RequestBody PlaceDto placeDto) {
        Place place = placeService.getPlaceById(id);

        place.setTitle(placeDto.title())
                .setDescription(placeDto.description())
                .setPrice(placeDto.price())
                .setAvailableFrom(placeDto.availableFrom())
                .setAvailableTo(placeDto.availableTo());

        return ResponseEntity.ok(PlaceMapper.toDto(placeService.saveOrUpdate(place)));
    }
}
