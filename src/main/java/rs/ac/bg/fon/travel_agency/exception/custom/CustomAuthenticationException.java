package rs.ac.bg.fon.travel_agency.exception.custom;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String message) {
        super(message);
    }
}
