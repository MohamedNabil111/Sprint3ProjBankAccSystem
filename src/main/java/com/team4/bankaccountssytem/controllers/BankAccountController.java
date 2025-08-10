package com.team4.bankaccountssytem.controllers;

import com.team4.bankaccountssytem.DTOs.BankAccountDTO;
import com.team4.bankaccountssytem.services.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class BankAccountController {

    private final BankAccountService service;

    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> create(
            @RequestParam String accountType,
            @RequestParam Long customerId
    ) {
        BankAccountDTO dto = service.createBankAccount(accountType, customerId);
        return ResponseEntity
                .created(URI.create("/api/accounts/" + dto.getId()))
                .body(dto);
    }

    @GetMapping
    public List<BankAccountDTO> list(
            @RequestParam(name = "minBalance", required = false)
            Double minBalance
    ) {
        return service.listAccounts(Optional.ofNullable(minBalance));
    }

    @GetMapping("/{id}")
    public BankAccountDTO getOne(@PathVariable Long id) {
        return service.getAccount(id);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(
            @RequestParam Long accountId,
            @RequestParam Double amount
    ) {
        service.deposit(accountId, amount);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{accountType}")
    public ResponseEntity<List<BankAccountDTO>> findByAccountType(@PathVariable String accountType)
    {
        List<BankAccountDTO> accounts = service.findByAccountType(accountType);
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{min}/{max}")
    public ResponseEntity<List<BankAccountDTO>> findByBalanceRange(@PathVariable Double min,@PathVariable Double max) {

        List<BankAccountDTO> accounts = service.findByBalanceBetween(min, max);

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(
            @RequestParam Long accountId,
            @RequestParam Double amount
    ) {
        service.withdraw(accountId, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam Double amount
    ) {
        service.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.noContent().build();
    }


}