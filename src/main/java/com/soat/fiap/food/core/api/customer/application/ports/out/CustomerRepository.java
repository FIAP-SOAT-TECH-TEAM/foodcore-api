package com.soat.fiap.food.core.api.customer.application.ports.out;

import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de clientes
 */
public interface CustomerRepository {
    
    /**
     * Salva um cliente
     * @param customer Cliente a ser salvo
     * @return Cliente salvo com ID gerado
     */
    Customer save(Customer customer);
    
    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return Optional contendo o cliente ou vazio se não encontrado
     */
    Optional<Customer> findById(Long id);
    
    /**
     * Busca um cliente por DOCUMENT
     * @param document DOCUMENT do cliente
     * @return Optional contendo o cliente ou vazio se não encontrado
     */
    Optional<Customer> findByDocument(String document);
    
    /**
     * Lista todos os clientes
     * @return Lista de clientes
     */
    List<Customer> findAll();
    
    /**
     * Lista apenas clientes ativos
     * @return Lista de clientes ativos
     */
    List<Customer> findAllActive();
    
    /**
     * Remove um cliente
     * @param id ID do cliente a ser removido
     */
    void delete(Long id);
} 