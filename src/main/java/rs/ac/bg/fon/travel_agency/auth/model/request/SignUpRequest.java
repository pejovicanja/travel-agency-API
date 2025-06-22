package rs.ac.bg.fon.travel_agency.auth.model.request;

import lombok.Builder;

@Builder
public record SignUpRequest(
        String firstName, String lastName, String email, String password, Integer age) {}
