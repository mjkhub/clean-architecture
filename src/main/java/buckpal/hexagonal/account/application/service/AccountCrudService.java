package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
class AccountCrudService implements AccountCrudUseCase {

    private final AccountCrudPort accountCrudPort;
    private final AtomicLong accountNumber = new AtomicLong();

    @Transactional
    @Override
    public Account createAccount(Member member, AccountCreateRequest accountCreateRequest) {

        Account account = Account.createAccount(generateAccountNumber(), member, accountCreateRequest);
        return accountCrudPort.saveAccount(account);
    }

    @Override
    public List<Account> getAccountsOfMember(Long memberId) {
        return accountCrudPort.getAccounts(memberId);
    }

    @Override
    public Account getAccountWithTransactions(Long memberId, String accountNumber, String transactionType) {

        return accountCrudPort.findByAccountWithTransactions(memberId, accountNumber, transactionType);
    }

    private String generateAccountNumber(){ // 계좌 번호는 유니크 -> An error could occur
        return "0000-0000-" + Long.toString(accountNumber.incrementAndGet());
    }



}
