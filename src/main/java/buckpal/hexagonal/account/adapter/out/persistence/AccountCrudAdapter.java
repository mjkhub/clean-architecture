package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AccountCrudAdapter implements AccountCrudPort {

    private final AccountJpaRepository accountRepository;

    @Override
    public Account findByAccountNumber(String number) {

        return accountRepository.findByNumber(number);
    }

    @Override
    public Account saveAccount(Account account) {

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts(Long memberId) {

        return accountRepository.findAccountsOfMember(memberId);
    }

    @Override
    public Account findByAccountWithTransactions(Long memberId, String number, String transactionType) {
        return accountRepository.findWithTransactionsQueryDsl(memberId, number, transactionType);
    }
}