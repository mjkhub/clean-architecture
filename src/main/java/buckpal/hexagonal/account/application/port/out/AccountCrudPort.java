package buckpal.hexagonal.account.application.port.out;

import buckpal.hexagonal.account.domain.Account;

public interface AccountCrudPort {

    Account findByAccountName(String name);
    void updateAccountMoney(Long id, int money);
    Account saveAccount(Account account);

}
