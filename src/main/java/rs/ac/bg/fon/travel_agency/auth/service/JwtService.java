package rs.ac.bg.fon.travel_agency.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

    Date extractExpiration(String token);
}
