package buckpal.hexagonal.transaction.application.port.out;

import java.util.List;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.domain.Transaction;

public interface TransactionCrudPort {

    Transaction save(Transaction transaction);

    List<Transaction> findAllByAccount(Account account);
}
