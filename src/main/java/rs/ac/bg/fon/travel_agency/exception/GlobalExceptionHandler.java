package rs.ac.bg.fon.travel_agency.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.bg.fon.travel_agency.exception.custom.NotFoundException;
import rs.ac.bg.fon.travel_agency.exception.custom.TokenRefreshException;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${application.url}")
    private String applicationUrl;

    private static final String ERROR_PATH = "/errors/";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> onMethodArgumentNotValidExceptionI(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Set<Violation> violations = getViolations(ex);

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.UNPROCESSABLE_ENTITY, violations.toString());
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "invalid-request"));
        problemDetail.setTitle("Invalid request");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", violations);
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> onNotFoundException(
            NotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Requested resource doesn't exist.");
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> onIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Illegal arguments");
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "invalid-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        Set<Violation> violations = getViolations(ex);

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.UNPROCESSABLE_ENTITY, violations.toString());
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "invalid-request"));
        problemDetail.setTitle("Invalid request");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", violations);
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Bad credentials. Invalid username/password");
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "authentication"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> onUncaughtException(
            Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "internal-server-error"));
        problemDetail.setTitle("Error occurred");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> accessDeniedException(
            AccessDeniedException e, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message =
                "User " + auth.getName() + " doesn't have required role. " + e.getMessage();
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("Authorization failed");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "authorization"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        return new ResponseEntity<>(problemDetail, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ProblemDetail> onTokenRefreshException(
            TokenRefreshException ex, HttpServletRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setType(URI.create(applicationUrl + ERROR_PATH + "token-refresh"));
        problemDetail.setTitle("Token refresh failed");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.FORBIDDEN);
    }

    private static Set<Violation> getViolations(MethodArgumentNotValidException ex) {
        Set<Violation> violations = new HashSet<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Violation violation =
                    new Violation(
                            fieldError.getField(),
                            fieldError.getDefaultMessage(),
                            OffsetDateTime.now());
            violations.add(violation);
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            Violation violation =
                    new Violation(error.getCode(), error.getDefaultMessage(), OffsetDateTime.now());
            violations.add(violation);
        }
        return violations;
    }

    private static Set<Violation> getViolations(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        return constraintViolations.stream()
                .map(
                        constraintViolation ->
                                new Violation(
                                        constraintViolation.getPropertyPath().toString(),
                                        constraintViolation.getMessage(),
                                        OffsetDateTime.now()))
                .collect(Collectors.toSet());
    }
}

record Violation(String field, String message, OffsetDateTime timestamp) {}
