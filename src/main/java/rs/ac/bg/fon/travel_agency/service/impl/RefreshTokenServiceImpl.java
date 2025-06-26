package rs.ac.bg.fon.travel_agency.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.travel_agency.domain.RefreshToken;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.exception.custom.NotFoundException;
import rs.ac.bg.fon.travel_agency.exception.custom.TokenRefreshException;
import rs.ac.bg.fon.travel_agency.repository.RefreshTokenRepository;
import rs.ac.bg.fon.travel_agency.service.RefreshTokenService;
import rs.ac.bg.fon.travel_agency.service.UserService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${refresh.token.duration.ms}")
    private Long refreshTokenDurationMs;

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository
                .findByTokenAndInvalidatedIsFalse(token)
                .orElseThrow(
                        () -> new NotFoundException("Refresh token " + token + " doesn't exist"));
    }

    @Override
    @Transactional
    public RefreshToken getOrCreateRefreshToken(User user) {
        RefreshToken userLatestRefreshToken = findLatestRefreshTokenByUser(user);
        if (userLatestRefreshToken != null
                && userLatestRefreshToken.getExpiryDate().compareTo(Instant.now()) > 0) {
            return userLatestRefreshToken;
        }

        RefreshToken refreshToken =
                new RefreshToken()
                        .setUser(userService.getUserById(user.getId()))
                        .setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                        .setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    @Transactional
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(
                    "Refresh token was expired. Please make a new signin request");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findLatestRefreshTokenByUser(User user) {
        return refreshTokenRepository.findUserLatestRefreshToken(user);
    }

    @Override
    @Transactional
    public void invalidateTokenByUser(User user) {
        refreshTokenRepository.invalidateTokensByUser(user);
    }
}
