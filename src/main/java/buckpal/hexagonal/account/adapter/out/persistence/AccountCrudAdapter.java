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

        return accountJpaRepository.findByNumber(name);
    }

    @Override
    public void updateAccountMoney(Long id, int money) {
        accountJpaRepository.updateMoney(id, money);
    }

    @Override
    public Account saveAccount(Account account) {

        return accountJpaRepository.save(account);
    }
}