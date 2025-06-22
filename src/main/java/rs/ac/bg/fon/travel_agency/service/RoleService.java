package rs.ac.bg.fon.travel_agency.service;

import rs.ac.bg.fon.travel_agency.domain.Role;

public interface RoleService {

    rs.ac.bg.fon.travel_agency.domain.Role getRoleByCode(Role.RoleCode code);
}
