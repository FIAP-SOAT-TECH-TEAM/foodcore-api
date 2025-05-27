package com.soat.fiap.food.core.api.user.application.services;

import com.soat.fiap.food.core.api.user.application.ports.in.RoleUseCase;
import com.soat.fiap.food.core.api.user.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Implementação do caso de uso de Roles.
 */
@Service
public class RoleService implements RoleUseCase {

    //TODO: Implementar o caso de uso de Roles

    @Override
    public Role createRole(Role role) {
        return null;
    }

    @Override
    public Role updateRole(Long id, Role role) {
        return null;
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }

    @Override
    public void deleteRole(Long id) {

    }
}
