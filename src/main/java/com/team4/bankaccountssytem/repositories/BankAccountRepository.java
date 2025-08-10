package com.team4.bankaccountssytem.repositories;

import com.team4.bankaccountssytem.entities.BankAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {

    List<BankAccount> findByAccountType(String accountType);

    List<BankAccount> findByBalanceGreaterThan(Double amount);

    @Query("SELECT b FROM BankAccount b WHERE b.balance BETWEEN :min AND :max")
    List<BankAccount> findByBalanceBetween(
            @Param("min") Double min,
            @Param("max") Double max
    );

    @Modifying
    @Transactional
    @Query(
            "UPDATE BankAccount b " +
                    "SET b.balance = b.balance + :delta " +
                    "WHERE b.id = :id"
    )
    int updateBalanceById(
            @Param("id") Long id,
            @Param("delta") Double delta
    );
}