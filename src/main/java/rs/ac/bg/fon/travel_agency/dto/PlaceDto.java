package rs.ac.bg.fon.travel_agency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import rs.ac.bg.fon.travel_agency.constraint.DateOrder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@DateOrder(
        dateFromField = "availableFrom",
        dateToField = "availableTo",
        message = "Available from date must be less or equal to available to date")
public record PlaceDto(
        Long id,
        @NotBlank String title,
        String description,
        String imageUrl,
        @NotNull @Positive Double price,
        @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate availableFrom,
        @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate availableTo,
        @NotNull UserDto user,
        @NotNull PlaceLocationDto placeLocation,
        String placeImageUrl) {}
