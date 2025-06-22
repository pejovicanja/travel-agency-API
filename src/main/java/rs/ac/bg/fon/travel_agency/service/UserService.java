package rs.ac.bg.fon.travel_agency.service;

import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;

import java.util.Set;

public interface UserService {

    User getUserById(Long id);

    void updateUserRoles(User user, Set<Role> roles);
}
