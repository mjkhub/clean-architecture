package buckpal.hexagonal.account.adapter.out.persistence;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;


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
