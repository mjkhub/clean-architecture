package buckpal.hexagonal.account.adapter.out.persistence;


import buckpal.hexagonal.account.domain.Account;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
class AccountMapper {

    public AccountJpaEntity mapToJpaEntity(Account account){
        return new AccountJpaEntity(account.getName(), account.getPassword(), account.getMoney(), LocalDate.now());
    }

    public Account mapToDomainEntity(AccountJpaEntity accountJpaEntity){
        return Account.createAccount(accountJpaEntity.getName(), accountJpaEntity.getPassword(),accountJpaEntity.getMoney());
    }
}
