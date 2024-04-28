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
        // 여기서 예외가 터질 수도 있음 잘 봐야함
        // session 있다면 memberdp
        Account account = Account.createAccount(generateAccountNumber(), member, accountCreateRequest);


        return accountCrudPort.saveAccount(account);
    }

    @Override
    public List<Account> getAccounts(Member member) {

        return null;
    }

    private String generateAccountNumber(){
        return "0000-0000-" + Long.toString(accountNumber.incrementAndGet());
    }



}
