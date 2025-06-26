package rs.ac.bg.fon.travel_agency.auth.model.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(@NotBlank String refreshToken) {}
