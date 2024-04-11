package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import buckpal.hexagonal.account.domain.State;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // use case
class SendMoneyService implements SendMoneyUseCase { // Adapter에서 직접적으로 유스케이스에 접근할 수 없도록

    private List<Account> accountList = new ArrayList<>();

    @Override
    public State sendMoney(SendMoneyRequest sendMoneyRequest) {
        String source = sendMoneyRequest.getSource();
        String target = sendMoneyRequest.getDestination();
        int money = sendMoneyRequest.getMoney();

        Account sourceAccount = findByName(source);
        Account targetAccount = findByName(target);

        State state = sourceAccount.transferMoney(targetAccount, money);

        return state;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initAccount(){
        Account jay = new Account("jay", 10000);
        Account park = new Account("park", 20000);
        accountList.add(jay);
        accountList.add(park);
    }

    private Account findByName(String name){
        Optional<Account> any = accountList.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();
        return any.orElse(null); //그냥 이렇게 쓰자
    }
}
