package com.a2sdm.need.doctors.jwt.services;

import com.a2sdm.need.doctors.jwt.model.Role;
import com.a2sdm.need.doctors.jwt.model.RoleName;
import com.a2sdm.need.doctors.jwt.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UtilServices {
    private final RoleRepository roleRepository;

    public Set<Role> getRolesFromStringToRole(Set<String> roles2) {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
            if (roleOptional.isEmpty()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }

    public Set<String> getRolesStringFromRole(Set<Role> roles2) {
        Set<String> roles = new HashSet<>();
        for (Role role : roles2) {

            roles.add(role.getName().toString());
        }
        return roles;
    }
}
