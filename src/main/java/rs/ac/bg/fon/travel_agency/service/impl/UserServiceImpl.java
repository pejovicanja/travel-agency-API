package rs.ac.bg.fon.travel_agency.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.exception.custom.NotFoundException;
import rs.ac.bg.fon.travel_agency.repository.UserRepository;
import rs.ac.bg.fon.travel_agency.service.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                       .findByEmail(username)
                       .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " can't be found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User with ID: " + id + " can't be found"));
    }

    @Override
    @Transactional
    public void updateUserRoles(User user, Set<Role> roles) {
        user.setRoles(roles);
        userRepository.save(user);
    }
}
