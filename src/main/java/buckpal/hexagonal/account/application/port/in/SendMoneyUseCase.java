package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.account.domain.dto.SendMoneyRequest;

public interface SendMoneyUseCase {

    AccountState sendMoney(SendMoneyRequest sendMoneyRequest);

}
