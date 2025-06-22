package rs.ac.bg.fon.travel_agency.auth.model.response;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record JwtAuthenticationResponse(
        Long id, String email, String token, OffsetDateTime expirationTime, String refreshToken) {}
