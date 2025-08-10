package com.team4.bankaccountssytem.services;

import com.team4.bankaccountssytem.DTOs.CustomerDTO;
import com.team4.bankaccountssytem.entities.Customer;
import com.team4.bankaccountssytem.mappers.CustomerMapper;
import com.team4.bankaccountssytem.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists");
        }

        if (customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Customer with phone " + customerDTO.getPhone() + " already exists");
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
        return customerMapper.toDTO(customer.get());
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.toDTOList(customers);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);
        if (existingCustomerOpt.isEmpty()) {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }

        Customer existingCustomer = existingCustomerOpt.get();

        if (!existingCustomer.getEmail().equals(customerDTO.getEmail()) &&
                customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists");
        }
        if (!existingCustomer.getPhone().equals(customerDTO.getPhone()) &&
                customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new RuntimeException("Customer with phone " + customerDTO.getPhone() + " already exists");
        }

        Customer updatedCustomer = customerMapper.updateEntityFromDTO(existingCustomer, customerDTO);
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return customerMapper.toDTO(savedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer with ID " + id + " not found");
        }
        customerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer with email " + email + " not found");
        }
        return customerMapper.toDTO(customer.get());
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomers(String searchTerm) {
        List<Customer> customers = customerRepository.findByNameOrEmailContaining(searchTerm);
        return customerMapper.toDTOList(customers);
    }

    @Transactional(readOnly = true)
    public long getTotalCustomerCount() {
        return customerRepository.count();
    }

    public List<CustomerDTO> findCustomersCreatedBetween(LocalDateTime startDate, LocalDateTime endDate)
    {
        List<Customer> customers = customerRepository.findCustomersCreatedBetween(startDate, endDate);
        return customerMapper.toDTOList(customers);
    }
}