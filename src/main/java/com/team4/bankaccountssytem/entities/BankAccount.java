package com.team4.bankaccountssytem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "bank_accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = "account_number")
)
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account number is required")
    @Column(
            name = "account_number",
            nullable = false,
            length = 32,
            unique = true
    )
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @NotNull(message = "Balance must not be null")
    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(
            mappedBy = "fromAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(
            mappedBy = "toAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public BankAccount() {
        // balance defaults to 0.0
    }

    public BankAccount(String accountNumber,
                       String accountType,
                       Customer customer) {
        this.accountNumber = accountNumber;
        this.accountType   = accountType;
        this.customer      = customer;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
    }

    public void setIncomingTransactions(List<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", customerId=" + (customer != null ? customer.getId() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}