package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.SendMoneyRequest;
import buckpal.hexagonal.account.domain.State;

public interface SendMoneyUseCase {

    State sendMoney(SendMoneyRequest sendMoneyRequest);
}
