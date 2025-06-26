package rs.ac.bg.fon.travel_agency.auth.model.response;

public record TokenRefreshResponse(String accessToken, String refreshToken, String tokenType) {
    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}
