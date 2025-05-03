package com.soat.fiap.food.core.api.customer.application.ports.in;

import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Porta de entrada para operações relacionadas a clientes
 */
public interface CustomerUseCase {
    
    /**
     * Cadastra um novo cliente
     * @param customer Cliente a ser cadastrado
     * @return Cliente cadastrado com ID gerado
     */
    Customer createCustomer(Customer customer);
    
    /**
     * Atualiza um cliente existente
     * @param id ID do cliente a ser atualizado
     * @param customer Cliente com dados atualizados
     * @return Cliente atualizado
     */
    Customer updateCustomer(Long id, Customer customer);
    
    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return Optional contendo o cliente ou vazio se não encontrado
     */
    Optional<Customer> getCustomerById(Long id);
    
    /**
     * Busca um cliente por DOCUMENT
     * @param document DOCUMENT do cliente
     * @return Optional contendo o cliente ou vazio se não encontrado
     */
    Optional<Customer> getCustomerByDocument(String document);
    
    /**
     * Lista todos os clientes
     * @return Lista de clientes
     */
    List<Customer> getAllCustomers();
    
    /**
     * Remove um cliente
     * @param id ID do cliente a ser removido
     */
    void deleteCustomer(Long id);
} 