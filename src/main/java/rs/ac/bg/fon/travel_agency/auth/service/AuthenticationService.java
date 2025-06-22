package rs.ac.bg.fon.travel_agency.auth.service;

import rs.ac.bg.fon.travel_agency.auth.model.request.SignInRequest;
import rs.ac.bg.fon.travel_agency.auth.model.request.SignUpRequest;
import rs.ac.bg.fon.travel_agency.auth.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
