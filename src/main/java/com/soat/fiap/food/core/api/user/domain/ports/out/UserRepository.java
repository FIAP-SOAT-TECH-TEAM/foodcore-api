package com.soat.fiap.food.core.api.user.domain.ports.out;

import com.soat.fiap.food.core.api.user.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de clientes
 */
public interface UserRepository {
    
    /**
     * Salva um usuário
     * @param user Usuário a ser salvo
     * @return Usuário salvo com ID gerado
     */
    User save(User user);
    
    /**
     * Busca um usuário por ID
     * @param id ID do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    Optional<User> findById(Long id);
    
    /**
     * Busca um usuário por DOCUMENT
     * @param document DOCUMENT do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    Optional<User> findByDocument(String document);
    
    /**
     * Lista todos os usuário
     * @return Lista de usuário
     */
    List<User> findAll();
    
    /**
     * Lista apenas usuário ativos
     * @return Lista de usuário ativos
     */
    List<User> findAllActive();
    
    /**
     * Remove um usuário
     * @param id ID do usuário a ser removido
     */
    void delete(Long id);
} 