package rs.ac.bg.fon.travel_agency.auth.model.request;

import lombok.Builder;

@Builder
public record SignInRequest(String email, String password) {}
