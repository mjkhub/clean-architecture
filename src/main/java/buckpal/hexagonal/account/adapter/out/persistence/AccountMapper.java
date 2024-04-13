package buckpal.hexagonal.account.adapter.out.persistence;


import buckpal.hexagonal.account.adapter.out.persistence.AccountJpaEntity;
import buckpal.hexagonal.account.domain.Account;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class AccountMapper {

    public AccountJpaEntity mapToJpaEntity(Account account){
        return new AccountJpaEntity(account.getName(), account.getPassword(), account.getMoney());
    }

    public Account mapToDomainEntity(AccountJpaEntity accountJpaEntity){
        return new Account(accountJpaEntity.getName(), accountJpaEntity.getPassword(),accountJpaEntity.getMoney());
    }
}
