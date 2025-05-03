package com.soat.fiap.food.core.api.customer.application.services;

import com.soat.fiap.food.core.api.customer.application.ports.in.CustomerUseCase;
import com.soat.fiap.food.core.api.customer.application.ports.out.CustomerRepository;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
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

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (!customer.isValidDocument()) {
            throw new IllegalArgumentException("DOCUMENT inválido");
        }

        Optional<Customer> existingCustomer = customerRepository.findByDocument(customer.getDocument());
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com este DOCUMENT");
        }
        
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.activate();
        
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        
        if (existingCustomer.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        
        customer.setId(id);
        customer.setCreatedAt(existingCustomer.get().getCreatedAt());
        customer.setUpdatedAt(LocalDateTime.now());
        
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByDocument(String document) {
        return customerRepository.findByDocument(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.delete(id);
    }
} 