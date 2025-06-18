package com.soat.fiap.food.core.api.user.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.user.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;

import java.util.List;
import java.util.Optional;

/**
 * Gateway para persistência do agregado Usuário.
 */
public class UserGateway {

    private final UserDataSource userDataSource;

    public UserGateway(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    /**
     * Salva o agregado Usuário.
     *
     * @param user Agregado Usuário a ser salvo
     * @return Usuário salvo com identificador atualizado
     */
    public User save(User user) {
        return userDataSource.save(user);
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    public Optional<User> findById(Long id) {
        return userDataSource.findById(id);
    }

    /**
     * Busca um usuário pelo documento.
     *
     * @param document Documento do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    public Optional<User> findByDocument(String document) {
        return userDataSource.findByDocument(document);
    }

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email E-mail do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    public Optional<User> findByEmail(String email) {
        return userDataSource.findByEmail(email);
    }

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username Nome de usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    public Optional<User> findByUsername(String username) {
        return userDataSource.findByUsername(username);
    }

    /**
     * Busca um usuário pela role.
     *
     * @param roleId ID da role do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    public Optional<User> findByRoleId(Long roleId) {
        return userDataSource.findByRoleId(roleId);
    }

    /**
     * Busca o primeiro usuário convidado (guest).
     *
     * @return Optional contendo o usuário convidado ou vazio se não encontrado
     */
    public Optional<User> findFirstByGuestTrue() {
        return userDataSource.findFirstByGuestTrue();
    }

    /**
     * Lista todos os usuários.
     *
     * @return Lista de usuários
     */
    public List<User> findAll() {
        return userDataSource.findAll();
    }

    /**
     * Lista apenas os usuários ativos.
     *
     * @return Lista de usuários ativos
     */
    public List<User> findAllActive() {
        return userDataSource.findAllActive();
    }

    /**
     * Remove um usuário com base em seu ID.
     *
     * @param id ID do usuário a ser removido
     */
    public void delete(Long id) {
        userDataSource.delete(id);
    }
}