package rs.ac.bg.fon.travel_agency.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.travel_agency.domain.Role;
import rs.ac.bg.fon.travel_agency.exception.custom.NotFoundException;
import rs.ac.bg.fon.travel_agency.repository.RoleRepository;
import rs.ac.bg.fon.travel_agency.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByCode(Role.RoleCode code) {
        return roleRepository
                .findByName(code)
                .orElseThrow(
                        () -> new NotFoundException("Role with code " + code + " doesn't exist"));
    }
}
