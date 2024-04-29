package buckpal.hexagonal.account.application.port.out;

import buckpal.hexagonal.account.domain.Account;

import java.util.List;

public interface AccountCrudPort {

    Account findByAccountNumber(String number);
    Account saveAccount(Account account);

    List<Account> getAccounts(Long memberId);

    Account findByAccountWithTransactions(Long memberId, String number);

}
