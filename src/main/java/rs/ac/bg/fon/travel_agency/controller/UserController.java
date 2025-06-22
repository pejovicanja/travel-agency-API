package rs.ac.bg.fon.travel_agency.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.dto.UserPatchRequest;
import rs.ac.bg.fon.travel_agency.service.RoleService;
import rs.ac.bg.fon.travel_agency.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PatchMapping("/{id}")
    // @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.ADMIN + "\")")
    public void updateUsersRoles(
            @PathVariable Long id, @RequestBody @Valid UserPatchRequest userPatchRequest) {
        User user = userService.getUserById(id);
        Set<Role> roles =
                userPatchRequest.roles().stream()
                        .map(r -> roleService.getRoleByCode(r.code()))
                        .collect(Collectors.toSet());
        userService.updateUserRoles(user, roles);
    }

}
