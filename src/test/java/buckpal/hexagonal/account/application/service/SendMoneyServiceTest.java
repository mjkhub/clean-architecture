package buckpal.hexagonal.account.application.service;

import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountState;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SendMoneyServiceTest {

    @Autowired
    SendMoneyUseCase sendMoneyUseCase;

    @Autowired
    AccountCrudPort accountCrudPort;

    @Test
    @DisplayName("정상 이체 통합 테스트")
    void sendMoney() {
        //given
        SendMoneyRequest sendMoneyRequest = new SendMoneyRequest("jay","park",1000);

        //when
        AccountState accountState = sendMoneyUseCase.sendMoney(sendMoneyRequest);
        Account sourceAccount = accountCrudPort.findAccount(sendMoneyRequest.getSource());


        //then
        assertThat(accountState.getName()).isEqualTo(sourceAccount.getName());
        assertThat(accountState.getMoney()).isEqualTo(sourceAccount.getMoney());
        System.out.println("sourceAccount.getMoney() = " + sourceAccount.getMoney());
    }

    @Test
    @DisplayName("이체중 예외 발생 통합 테스트")
    void sendMoneyEx() {
        //given
        SendMoneyRequest sendMoneyRequest = new SendMoneyRequest("jay","park",11000);

        //when
        assertThatThrownBy( ()->sendMoneyUseCase.sendMoney(sendMoneyRequest))
                .isInstanceOf(IllegalStateException.class);
        Account sourceAccount = accountCrudPort.findAccount(sendMoneyRequest.getSource());

        //then
        assertThat(sourceAccount.getMoney()).isEqualTo(10000);
        System.out.println("sourceAccount.getMoney() = " + sourceAccount.getMoney());
    }
}