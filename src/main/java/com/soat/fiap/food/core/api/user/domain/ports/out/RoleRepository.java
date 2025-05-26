package com.soat.fiap.food.core.api.user.application.ports.out;

import com.soat.fiap.food.core.api.user.domain.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de roles
 */
public interface RoleRepository {

    /**
     * Salva uma role
     * @param role Role a ser salva
     * @return Role salva com ID gerado
     */
    Role save(Role role);

    /**
     * Busca uma role por ID
     * @param id ID da Role
     * @return Optional contendo a role ou vazio se não encontrada
     */
    Optional<Role> findById(Long id);

    /**
     * Lista todos as roles
     * @return Lista de roles
     */
    List<Role> findAll();

    /**
     * Remove uma role
     * @param id ID da role a ser removida
     */
    void delete(Long id);
}