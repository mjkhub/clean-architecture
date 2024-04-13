package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AccountCrudAdapter implements AccountCrudPort {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountMapper accountMapper = new AccountMapper();

    @Override
    public Account findAccount(String name) {
        AccountJpaEntity accountJpaEntity = accountJpaRepository.findByName(name);
        return accountMapper.mapToDomainEntity(accountJpaEntity);
    }

    @Override
    public void updateAccount(String name, int money) {
        accountJpaRepository.update(name,money);
    }
}
