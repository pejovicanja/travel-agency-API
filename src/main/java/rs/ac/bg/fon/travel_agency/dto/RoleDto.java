package rs.ac.bg.fon.travel_agency.dto;

import jakarta.validation.constraints.NotNull;
import rs.ac.bg.fon.travel_agency.domain.Role;

public record RoleDto(@NotNull Role.RoleCode code) {}
