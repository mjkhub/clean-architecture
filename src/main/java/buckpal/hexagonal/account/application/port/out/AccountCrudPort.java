package buckpal.hexagonal.account.application.port.out;

import buckpal.hexagonal.account.domain.Account;


public interface AccountCrudPort {

    Account saveAccount(Account account);

    Account findByAccountNumber(String accountNumber);

    Account findById(Long accountId);
}
