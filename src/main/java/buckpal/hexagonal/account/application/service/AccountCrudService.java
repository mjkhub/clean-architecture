package buckpal.hexagonal.account.application.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;

@Service
@RequiredArgsConstructor
class AccountCrudService implements AccountCrudUseCase {

    private final AccountCrudPort accountCrudPort;
    private final AtomicLong accountNumber = new AtomicLong();

    @Transactional
    @Override
    public Account createAccount() {

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .balance(0)
                .signUpDate(LocalDate.now())
                .build();
        return accountCrudPort.saveAccount(account);
    }

    private String generateAccountNumber(){ // 계좌 번호는 유니크 -> An error could occur
        return "0000-0000-" + Long.toString(accountNumber.incrementAndGet());
    }

}
