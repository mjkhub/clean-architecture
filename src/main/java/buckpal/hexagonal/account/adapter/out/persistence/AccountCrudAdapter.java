package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AccountCrudAdapter implements AccountCrudPort {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account findByAccountName(String name) {

        return accountJpaRepository.findByName(name);
    }

    @Override
    public void updateAccount(String name, int money) {
        accountJpaRepository.update(name,money);
    }

    @Override
    public Account saveAccount(Account account) {

        return accountJpaRepository.save(account);
    }
}