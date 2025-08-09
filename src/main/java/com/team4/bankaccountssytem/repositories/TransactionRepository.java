package com.team4.bankaccountssytem.repositories;

import com.team4.bankaccountssytem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Finds all transactions where the given account ID is either the sender or the receiver.
     * @param accountId The ID of the bank account.
     * @return A list of transactions sorted by timestamp in descending order.
     */
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.timestamp DESC")
    List<Transaction> findAllByAccountId(@Param("accountId") Long accountId);

    /**
     * Deletes all transactions that occurred before a specified cutoff date.
     * @param cutoffDate The date before which transactions will be deleted.
     */
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.timestamp < :cutoffDate")
    void deleteOldTransactions(@Param("cutoffDate") LocalDateTime cutoffDate);

}
