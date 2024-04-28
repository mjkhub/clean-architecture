package buckpal.hexagonal.account.application.port.out;

import buckpal.hexagonal.account.domain.Account;

public interface AccountCrudPort {

    Account findByAccountName(String name);
    void updateAccount(String name, int money);
    Account saveAccount(Account account);

}
