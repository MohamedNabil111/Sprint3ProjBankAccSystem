package com.team4.bankaccountssytem.repositories;

import com.team4.bankaccountssytem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    List<Customer> findByNameContainingIgnoreCase(String name);
    Optional<Customer> findByPhone(String phone);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Custom query to find customers by name or email
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Customer> findByNameOrEmailContaining(@Param("searchTerm") String searchTerm);

    // Custom query to count customers
    @Query("SELECT COUNT(c) FROM Customer c")
    long countAllCustomers();

    // Custom update query to update customer information
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.name = :name, c.phone = :phone, c.address = :address " +
            "WHERE c.id = :id")
    int updateCustomerInfo(@Param("id") Long id,
                           @Param("name") String name,
                           @Param("phone") String phone,
                           @Param("address") String address);

    // Custom delete query to soft delete customer (if needed in future)
    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c WHERE c.id = :id")
    int deleteCustomerById(@Param("id") Long id);

    // Find customers created in a date range (example for future use)
    @Query("SELECT c FROM Customer c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<Customer> findCustomersCreatedBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                               @Param("endDate") java.time.LocalDateTime endDate);
}