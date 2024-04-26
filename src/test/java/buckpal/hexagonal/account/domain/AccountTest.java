package buckpal.hexagonal.account.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("정상 이체 ")
    void transferMoney() {
        //given
        Account account1 = Account.createAccount("jay", "1234", 10000);
        Account account2 = Account.createAccount("park", "1234", 20000);

        //when
        int money = 8000;
        AccountState transferResult = account1.transferMoney(account2, money);

        //then
        assertThat(account1.getMoney()).isEqualTo(10000-money);
        assertThat(account2.getMoney()).isEqualTo(20000+money);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void transferMoneyException() {
        //given
        int ogMoney1 = 10000;
        int ogMoney2 = 20000;
        Account account1 = Account.createAccount("jay", "1234", ogMoney1);
        Account account2 = Account.createAccount("park", "1234",ogMoney2);

        //when
        int money = 11000;
        assertThatThrownBy(() -> account1.transferMoney(account2, money))
                .isInstanceOf(IllegalStateException.class);

        //then
        assertThat(account1.getMoney()).isEqualTo(ogMoney1);
        assertThat(account2.getMoney()).isEqualTo(ogMoney2);


    }
}