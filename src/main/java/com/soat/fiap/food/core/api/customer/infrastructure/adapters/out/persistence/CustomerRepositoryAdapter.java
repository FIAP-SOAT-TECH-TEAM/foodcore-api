package com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence;

import com.soat.fiap.food.core.api.customer.application.ports.out.CustomerRepository;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.mapper.CustomerEntityMapper;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.repository.SpringDataCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída CustomerRepository
 */
@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final SpringDataCustomerRepository springDataCustomerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public CustomerRepositoryAdapter(
            SpringDataCustomerRepository springDataCustomerRepository,
            CustomerEntityMapper customerEntityMapper) {
        this.springDataCustomerRepository = springDataCustomerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    @Override
    public Customer save(Customer customer) {
        var customerEntity = customerEntityMapper.toEntity(customer);
        var savedEntity = springDataCustomerRepository.save(customerEntity);
        return customerEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return springDataCustomerRepository.findById(id)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByDocument(String document) {
        return springDataCustomerRepository.findByDocument(document)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        var customerEntities = springDataCustomerRepository.findAll();
        return customerEntityMapper.toDomainList(customerEntities);
    }

    @Override
    public List<Customer> findAllActive() {
        var customerEntities = springDataCustomerRepository.findByActiveTrue();
        return customerEntityMapper.toDomainList(customerEntities);
    }

    @Override
    public void delete(Long id) {
        springDataCustomerRepository.deleteById(id);
    }
} 