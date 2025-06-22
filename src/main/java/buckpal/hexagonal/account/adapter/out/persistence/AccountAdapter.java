package buckpal.hexagonal.account.adapter.out.persistence;

import org.springframework.stereotype.Component;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
class AccountAdapter implements AccountCrudPort {

    private final AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
