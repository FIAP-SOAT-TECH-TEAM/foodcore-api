package com.soat.fiap.food.core.api.user.application.ports.in;

import com.soat.fiap.food.core.api.user.domain.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Porta de entrada para operações relacionadas a roles
 */
public interface RoleUseCase {

    /**
     * Cadastra uma nova role
     * @param role Role a ser cadastrada
     * @return Role cadastrada com ID gerado
     */
    Role createRole(Role role);

    /**
     * Atualiza uma role existente
     * @param id ID da role a ser atualizada
     * @param role Role com dados atualizados
     * @return Role atualizada
     */
    Role updateRole(Long id, Role role);

    /**
     * Busca uma Role por ID
     * @param id ID da Role
     * @return Optional contendo a Role ou vazio se não encontrado
     */
    Optional<Role> getRoleById(Long id);


    /**
     * Lista todos as Roles
     * @return Lista de todas as Roles
     */
    List<Role> getAllRoles();

    /**
     * Remove uma role
     * @param id ID da Role a ser removido
     */
    void deleteRole(Long id);
}
