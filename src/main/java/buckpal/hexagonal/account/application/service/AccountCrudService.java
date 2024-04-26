package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AccountCrudService implements AccountCrudUseCase {

    private final AccountCrudPort accountCrudPort;

    @Transactional
    @Override
    public Account createAccount(AccountCreateRequest accountCreateRequest) {
        // 여기서 예외가 터질 수도 있음 잘 봐야함
        Account account = Account.createAccount(accountCreateRequest.getName(), accountCreateRequest.getPassword(), accountCreateRequest.getMoney());
        return accountCrudPort.saveAccount(account);
    }



}
