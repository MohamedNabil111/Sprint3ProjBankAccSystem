package com.team4.bankaccountssytem.mappers;

import com.team4.bankaccountssytem.DTOs.CustomerDTO;
import com.team4.bankaccountssytem.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());

        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());

        return customer;
    }

    public List<CustomerDTO> toDTOList(List<Customer> customers) {
        if (customers == null) {
            return null;
        }

        return customers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Customer> toEntityList(List<CustomerDTO> customerDTOs) {
        if (customerDTOs == null) {
            return null;
        }

        return customerDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Customer updateEntityFromDTO(Customer existingCustomer, CustomerDTO customerDTO) {
        if (existingCustomer == null || customerDTO == null) {
            return existingCustomer;
        }

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setAddress(customerDTO.getAddress());
        // ma7dsh y3mel update le el ID, createdAt, and updatedAt

        return existingCustomer;
    }
}