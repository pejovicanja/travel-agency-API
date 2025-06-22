package rs.ac.bg.fon.travel_agency.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.travel_agency.auth.model.request.SignInRequest;
import rs.ac.bg.fon.travel_agency.auth.model.request.SignUpRequest;
import rs.ac.bg.fon.travel_agency.auth.model.response.JwtAuthenticationResponse;
import rs.ac.bg.fon.travel_agency.auth.service.AuthenticationService;
import rs.ac.bg.fon.travel_agency.auth.service.JwtService;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.repository.UserRepository;
import rs.ac.bg.fon.travel_agency.service.RoleService;

import java.time.ZoneOffset;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    @Override
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user =
                User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .age(request.age())
                        .roles(Set.of(roleService.getRoleByCode(Role.RoleCode.USER_BASIC)))
                        .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .token(jwt)
                .expirationTime(
                        jwtService
                                .extractExpiration(jwt)
                                .toInstant()
                                .atOffset(ZoneOffset.ofHours(2)))
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user =
                userRepository
                        .findByEmail(request.email())
                        .orElseThrow(
                                () -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .token(jwt)
                .expirationTime(
                        jwtService
                                .extractExpiration(jwt)
                                .toInstant()
                                .atOffset(ZoneOffset.ofHours(2)))
                .email(user.getEmail())
                .build();
    }

    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
