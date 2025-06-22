package rs.ac.bg.fon.travel_agency.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.travel_agency.domain.PlaceLocation;
import rs.ac.bg.fon.travel_agency.dto.PlaceLocationDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceLocationMapper {
    public static PlaceLocation toEntity(PlaceLocationDto placeLocationDto) {
        return PlaceLocation.builder()
                .address(placeLocationDto.address())
                .staticMapImageUrl(placeLocationDto.staticMapImageUrl())
                .lat(placeLocationDto.lat())
                .lng(placeLocationDto.lng())
                .build();
    }

    public static PlaceLocationDto toDto(PlaceLocation placeLocation) {
        return PlaceLocationDto.builder()
                .address(placeLocation.getAddress())
                .staticMapImageUrl(placeLocation.getStaticMapImageUrl())
                .lat(placeLocation.getLat())
                .lng(placeLocation.getLng())
                .build();
    }
}
