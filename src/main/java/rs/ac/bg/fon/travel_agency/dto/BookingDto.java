package rs.ac.bg.fon.travel_agency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import rs.ac.bg.fon.travel_agency.constraint.DateOrder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@DateOrder(
        dateFromField = "bookedFrom",
        dateToField = "bookedTo",
        message = "Booked from date must be less or equal to booked to date")
public record BookingDto(
        Long id,
        @NotBlank String title,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull Integer guestNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedFrom,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedTo,
        @NotNull UserDto user,
        @NotNull PlaceDto place) {}
