package rs.ac.bg.fon.travel_agency.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import rs.ac.bg.fon.travel_agency.domain.RefreshToken;
import rs.ac.bg.fon.travel_agency.domain.User;

@Validated
public interface RefreshTokenService {

    RefreshToken findByToken(@NotBlank String token);

    RefreshToken getOrCreateRefreshToken(@NotNull User user);

    void verifyExpiration(@Valid RefreshToken refreshToken);

    RefreshToken findLatestRefreshTokenByUser(@NotNull User user);

    void invalidateTokenByUser(User user);
}
