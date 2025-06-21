package buckpal.hexagonal.transaction.adapter.out.persistence;


import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccount(Account sourceAccount);
}

