package buckpal.hexagonal.transaction.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.domain.Transaction;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
class TransactionAdapter implements TransactionCrudPort {

    private final TransactionJpaRepository repository;

    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<Transaction> findAllByAccount(Account account) {
        return repository.findBySourceAccount(account);
    }
}
