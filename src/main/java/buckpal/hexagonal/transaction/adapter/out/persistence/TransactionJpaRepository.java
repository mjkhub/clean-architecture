package buckpal.hexagonal.transaction.adapter.out.persistence;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.domain.Transaction;

interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccount(Account sourceAccount);
}
