package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
class AccountCrudService implements AccountCrudUseCase {

    private final AccountCrudPort accountCrudPort;
    private final AtomicLong accountNumber = new AtomicLong();

    @Transactional
    @Override
    public Account createAccount(Member member, int money) {
        // 여기서 예외가 터질 수도 있음 잘 봐야함
        // session 있다면 memberdp
        Account.createAccount(generateAccountNumber(), member, money);

        return null;
    }



    private String generateAccountNumber(){
        return Long.toString(accountNumber.incrementAndGet());
    }



}
