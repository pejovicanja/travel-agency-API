package rs.ac.bg.fon.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.travel_agency.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Role.RoleCode code);
}
