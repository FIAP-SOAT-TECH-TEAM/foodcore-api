package com.soat.fiap.food.core.api.customer.application.services;

import com.soat.fiap.food.core.api.customer.application.ports.in.CustomerUseCase;
import com.soat.fiap.food.core.api.customer.application.ports.out.CustomerRepository;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Cliente
 */
@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomLogger logger;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        logger.debug("Criando cliente com documento: {}", customer.getDocument());
        
        if (!customer.isValidDocument()) {
            logger.warn("Tentativa de criar cliente com documento inválido: {}", customer.getDocument());
            throw new BusinessException("Documento inválido");
        }

        Optional<Customer> existingCustomer = customerRepository.findByDocument(customer.getDocument());
        if (existingCustomer.isPresent()) {
            logger.warn("Tentativa de criar cliente com documento já existente: {}", customer.getDocument());
            throw new ResourceConflictException("cliente", "documento", customer.getDocument());
        }
        
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.activate();
        
        Customer savedCustomer = customerRepository.save(customer);
        logger.debug("Cliente criado com sucesso. ID: {}", savedCustomer.getId());
        
        return savedCustomer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        logger.debug("Atualizando cliente com ID: {}", id);
        
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        
        if (existingCustomer.isEmpty()) {
            logger.warn("Cliente não encontrado com ID: {}", id);
            throw new ResourceNotFoundException("Cliente", "id", id);
        }
        
        if (!existingCustomer.get().getDocument().equals(customer.getDocument())) {
            Optional<Customer> customerWithSameDocument = customerRepository.findByDocument(customer.getDocument());
            if (customerWithSameDocument.isPresent() && !customerWithSameDocument.get().getId().equals(id)) {
                logger.warn("Tentativa de atualizar cliente para documento já existente: {}", customer.getDocument());
                throw new ResourceConflictException("cliente", "documento", customer.getDocument());
            }
        }
        
        customer.setId(id);
        customer.setCreatedAt(existingCustomer.get().getCreatedAt());
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer updatedCustomer = customerRepository.save(customer);
        logger.debug("Cliente atualizado com sucesso. ID: {}", updatedCustomer.getId());
        
        return updatedCustomer;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(Long id) {
        logger.debug("Buscando cliente com ID: {}", id);
        Optional<Customer> customer = customerRepository.findById(id);
        
        if (customer.isEmpty()) {
            logger.debug("Cliente não encontrado com ID: {}", id);
        } else {
            logger.debug("Cliente encontrado. Nome: {}", customer.get().getName());
        }
        
        return customer;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByDocument(String document) {
        logger.debug("Buscando cliente com documento: {}", document);
        return customerRepository.findByDocument(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        logger.debug("Buscando todos os clientes");
        List<Customer> customers = customerRepository.findAll();
        logger.debug("Total de clientes encontrados: {}", customers.size());
        return customers;
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        logger.debug("Excluindo cliente com ID: {}", id);
        
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            logger.warn("Tentativa de excluir cliente inexistente. ID: {}", id);
            throw new ResourceNotFoundException("Cliente", "id", id);
        }
        
        customerRepository.delete(id);
        logger.debug("Cliente excluído com sucesso. ID: {}", id);
    }
}