package buckpal.hexagonal.account.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void subMoneyShouldDeductBalanceWhenSufficientFunds() {

        // given
        Account account = Account.builder()
                .balance(1000)
                .build();
        // when
        account.subMoney(500);

        // then
        assertEquals(500, account.getBalance());
    }

    @Test
    void subMoneyShouldThrowExceptionWhenInsufficientFunds() {
        // given
        Account account = Account.builder()
                .balance(300)
                .build();

        // when
        assertThrows(IllegalArgumentException.class, () -> account.subMoney(500),
                "Account balance is smaller than amount");
    }

    @Test
    void addMoneyShouldIncreaseBalance() {
        // given
        Account account = Account.builder()
                .balance(500)
                .build();

        // when
        account.addMoney(300);

        // then
        assertEquals(800, account.getBalance());
    }

}