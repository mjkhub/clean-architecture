package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountState;
import buckpal.hexagonal.account.domain.SendMoneyRequest;

public interface SendMoneyUseCase {

    AccountState sendMoney(SendMoneyRequest sendMoneyRequest);

}
