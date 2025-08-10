package com.team4.bankaccountssytem.mappers;

import com.team4.bankaccountssytem.DTOs.BankAccountDTO;
import com.team4.bankaccountssytem.entities.BankAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccountDTO toDTO(BankAccount acct) {
        if (acct == null) {
            return null;
        }
        BankAccountDTO dto = new BankAccountDTO();
        dto.setId(acct.getId());
        dto.setAccountNumber(acct.getAccountNumber());
        dto.setAccountType(acct.getAccountType());
        dto.setBalance(acct.getBalance());
        dto.setCustomerId(acct.getCustomer().getId());
        return dto;
    }

    public BankAccount toEntity(BankAccountDTO dto) {
        if (dto == null) {
            return null;
        }
        BankAccount acct = new BankAccount();
        acct.setAccountNumber(dto.getAccountNumber());
        acct.setAccountType(dto.getAccountType());
        acct.setBalance(dto.getBalance());
        return acct;
    }

    public List<BankAccountDTO> toDTOList(List<BankAccount> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}