package com.team4.bankaccountssytem.controllers;

import com.team4.bankaccountssytem.DTOs.TransactionDTO;
import com.team4.bankaccountssytem.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * GET /api/v1/accounts/{accountId}/transactions : Get all transactions for a specific account.
     *
     * @param accountId The ID of the bank account.
     * @return A ResponseEntity containing a list of TransactionDTOs and an OK status.
     */
    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable Long accountId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * GET /api/v1/transactions/{id} : Get a single transaction by its ID.
     *
     * @param id The ID of the transaction.
     * @return A ResponseEntity containing the TransactionDTO and an OK status.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transactionDTO = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }
}
