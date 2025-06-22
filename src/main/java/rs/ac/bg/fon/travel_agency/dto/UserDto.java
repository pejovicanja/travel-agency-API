package rs.ac.bg.fon.travel_agency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Long id, String email, String password, String firstName, String lastName, Integer age) {}
