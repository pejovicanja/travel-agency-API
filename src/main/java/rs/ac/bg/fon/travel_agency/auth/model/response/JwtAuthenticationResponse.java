package rs.ac.bg.fon.travel_agency.auth.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtAuthenticationResponse(
        Long id, String email, String token, OffsetDateTime expirationTime, String refreshToken) {}
