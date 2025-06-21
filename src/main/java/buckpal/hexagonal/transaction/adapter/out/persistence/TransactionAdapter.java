package buckpal.hexagonal.transaction.adapter.out.persistence;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


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
