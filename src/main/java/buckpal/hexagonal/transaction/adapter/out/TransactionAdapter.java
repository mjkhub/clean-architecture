package buckpal.hexagonal.transaction.adapter.out;

import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
class TransactionAdapter implements TransactionCrudPort {

    private final TransactionJpaRepository repository;
    @Override
    public Transaction save(Transaction transaction) {

        return repository.save(transaction);
    }
}
