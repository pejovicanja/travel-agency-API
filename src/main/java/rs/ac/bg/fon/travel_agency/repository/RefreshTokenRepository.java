package rs.ac.bg.fon.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rs.ac.bg.fon.travel_agency.domain.RefreshToken;
import rs.ac.bg.fon.travel_agency.domain.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndInvalidatedIsFalse(String token);

    @Query(
            "SELECT r FROM RefreshToken r WHERE r.user = :user AND r.invalidated = false ORDER BY r.expiryDate DESC LIMIT 1")
    RefreshToken findUserLatestRefreshToken(User user);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.invalidated = true WHERE r.user = :user")
    void invalidateTokensByUser(User user);
}
