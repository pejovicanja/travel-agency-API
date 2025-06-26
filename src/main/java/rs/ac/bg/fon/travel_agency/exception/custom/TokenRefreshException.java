package rs.ac.bg.fon.travel_agency.exception.custom;

public class TokenRefreshException extends RuntimeException {

    public TokenRefreshException(String message) {
        super(message);
    }
}
