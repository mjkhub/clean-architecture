package buckpal.hexagonal.transaction.application.port.out;

import buckpal.hexagonal.transaction.domain.Transaction;

public interface TransactionCrudPort {

    Transaction save(Transaction transaction);
}
