package com.team4.bankaccountssytem.controllers;

import com.team4.bankaccountssytem.DTOs.CustomerDTO;
import com.team4.bankaccountssytem.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// I used GPT for making test cases.
// We should probably do something, if a user enters a wrong url,
// like completely wrong url /api/potatoes for example
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create Customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // Fetch all Customers, we may need some form of authentication
    // 3ashan mesh sa7 en sada7 mada7 ely 3aref el API ygb ely howa 3awozo
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Get Customer by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // Edit customer given id
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // Delete customer by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Customer with ID " + id + " has been deleted successfully");
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Fetch by email
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email) {
        CustomerDTO customer = customerService.getCustomerByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * law 3ayez tdwar bel esm aw el email fe example lel url
     * /api/customer/search?term=jane.smith
     * /api/customer/search?term=John
     */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(
            @RequestParam("term") String searchTerm) {
        List<CustomerDTO> customers = customerService.searchCustomers(searchTerm);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Check if a customer exists, I think it's overkill sa7?
    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> checkCustomerExists(@PathVariable Long id) {
        boolean exists = customerService.customerExists(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Count customers
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCustomerCount() {
        long count = customerService.getTotalCustomerCount();
        Map<String, Long> response = new HashMap<>();
        response.put("totalCustomers", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/created-between")
    public ResponseEntity<List<CustomerDTO>> findCustomersCreatedBetween(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate)
    {
        List<CustomerDTO> customers = customerService.findCustomersCreatedBetween(startDate, endDate);

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

}