package com.team4.bankaccountssytem.services;

import com.team4.bankaccountssytem.DTOs.BankAccountDTO;
import com.team4.bankaccountssytem.entities.BankAccount;
import com.team4.bankaccountssytem.entities.TransactionType;
import com.team4.bankaccountssytem.exceptions.ResourceNotFoundException;
import com.team4.bankaccountssytem.mappers.BankAccountMapper;
import com.team4.bankaccountssytem.repositories.BankAccountRepository;
import com.team4.bankaccountssytem.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountService {

    private final BankAccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;
    private final BankAccountMapper mapper;

    @Autowired
    public BankAccountService(
            BankAccountRepository accountRepository,
            CustomerRepository customerRepository,
            TransactionService transactionService,
            BankAccountMapper mapper
    ) {
        this.accountRepository   = accountRepository;
        this.customerRepository  = customerRepository;
        this.transactionService  = transactionService;
        this.mapper              = mapper;
    }

    public BankAccountDTO createBankAccount(String accountType, Long customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found: " + customerId)
                );

        String acctNum = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16);

        BankAccount acct = new BankAccount();
        acct.setAccountNumber(acctNum);
        acct.setAccountType(accountType);
        acct.setCustomer(customer);

        BankAccount saved = accountRepository.save(acct);
        return mapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<BankAccountDTO> listAccounts(Optional<Double> minBalance) {
        List<BankAccount> list = minBalance
                .map(accountRepository::findByBalanceGreaterThan)
                .orElseGet(accountRepository::findAll);

        return list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BankAccountDTO getAccount(Long id) {
        BankAccount acct = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found: " + id)
                );
        return mapper.toDTO(acct);
    }

    public void deposit(Long accountId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        BankAccount acct = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found: " + accountId)
                );
        acct.setBalance(acct.getBalance() + amount);
        accountRepository.save(acct);

        transactionService.createAndSaveTransaction(
                BigDecimal.valueOf(amount),
                TransactionType.DEPOSIT,
                acct.getCustomer(),
                null,
                acct
        );
    }

    public void withdraw(Long accountId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        BankAccount acct = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found: " + accountId)
                );
        if (acct.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        acct.setBalance(acct.getBalance() - amount);
        accountRepository.save(acct);

        transactionService.createAndSaveTransaction(
                BigDecimal.valueOf(amount),
                TransactionType.WITHDRAWAL,
                acct.getCustomer(),
                acct,
                null
        );
    }

    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        withdraw(fromAccountId, amount);
        deposit(toAccountId, amount);
    }
}