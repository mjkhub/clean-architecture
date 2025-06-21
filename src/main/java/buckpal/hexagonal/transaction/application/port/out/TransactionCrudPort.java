package buckpal.hexagonal.transaction.application.port.out;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.domain.Transaction;

import java.util.List;

public interface TransactionCrudPort {

    Transaction save(Transaction transaction);

    List<Transaction> findAllByAccount(Account account);
}
