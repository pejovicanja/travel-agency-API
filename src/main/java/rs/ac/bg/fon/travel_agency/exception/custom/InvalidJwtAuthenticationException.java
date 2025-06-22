package rs.ac.bg.fon.travel_agency.exception.custom;

public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
