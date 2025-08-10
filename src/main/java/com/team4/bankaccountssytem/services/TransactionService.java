package com.team4.bankaccountssytem.services;

import com.team4.bankaccountssytem.DTOs.TransactionDTO;
import com.team4.bankaccountssytem.entities.BankAccount;
import com.team4.bankaccountssytem.entities.Customer;
import com.team4.bankaccountssytem.entities.Transaction;
import com.team4.bankaccountssytem.entities.TransactionType;
import com.team4.bankaccountssytem.exceptions.ResourceNotFoundException;
import com.team4.bankaccountssytem.mappers.TransactionMapper;
import com.team4.bankaccountssytem.repositories.TransactionRepository;
import com.team4.bankaccountssytem.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository; // To verify account existence

    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Retrieves all transactions for a specific bank account.
     * @param accountId The ID of the bank account.
     * @return A list of TransactionDTOs.
     * @throws ResourceNotFoundException if the bank account does not exist.
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsForAccount(Long accountId) {
        // First, ensure the account exists. Your BankAccountService/Repository would handle this.
        if (!bankAccountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Bank Account not found with id: " + accountId);
        }

        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single transaction by its ID.
     * @param transactionId The ID of the transaction to retrieve.
     * @return The corresponding TransactionDTO.
     * @throws ResourceNotFoundException if no transaction is found with the given ID.
     */
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        return TransactionMapper.toDto(transaction);
    }

    /**
     * Creates and saves a transaction record. This is the central method for logging all financial movements.
     * It's called by other services (like BankAccountService) after they perform an operation.
     *
     * @param amount The amount of the transaction.
     * @param type The type of transaction (DEPOSIT, WITHDRAWAL, TRANSFER).
     * @param customer The customer who initiated the transaction.
     * @param fromAccount The source account (can be null for deposits).
     * @param toAccount The destination account (can be null for withdrawals).
     * @return The saved Transaction entity.
     */
    public Transaction createAndSaveTransaction(BigDecimal amount, TransactionType type, Customer customer, BankAccount fromAccount, BankAccount toAccount) {
        Transaction transaction = new Transaction(type, amount, customer, fromAccount, toAccount);
        return transactionRepository.save(transaction);
    }
}
