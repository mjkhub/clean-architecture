package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountState;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // use case
@RequiredArgsConstructor
class SendMoneyService implements SendMoneyUseCase { // Adapter에서 직접적으로 유스케이스에 접근할 수 없도록

    private final AccountCrudPort accountCrudPort;

    @Transactional
    @Override
    public AccountState sendMoney(SendMoneyRequest sendMoneyRequest) {
        String source = sendMoneyRequest.getSource();
        String target = sendMoneyRequest.getDestination();
        int money = sendMoneyRequest.getMoney();

        Account sAccount = accountCrudPort.findAccount(source);
        Account tAccount = accountCrudPort.findAccount(target);

        AccountState accountState = sAccount.transferMoney(tAccount, money); //비즈니스로직
        accountCrudPort.updateAccount(accountState.getName(), accountState.getMoney()); //db 반영

        return accountState;
    }

}
