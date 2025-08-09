package com.team4.bankaccountssytem.mappers;

import com.team4.bankaccountssytem.DTOs.TransactionDTO;
import com.team4.bankaccountssytem.entities.Transaction;

public class TransactionMapper {
    public static TransactionDTO toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setCustomerId(transaction.getCustomer().getId());

        // Handle nullable accounts
//        if (transaction.getFromAccount() != null) {
//            dto.setFromAccountId(transaction.getFromAccount().getId());
//        }
//        if (transaction.getToAccount() != null) {
//            dto.setToAccountId(transaction.getToAccount().getId());
//        }

        return dto;
    }
}
