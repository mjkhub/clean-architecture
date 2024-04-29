package buckpal.hexagonal.transaction.application.port.in;

import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;

public interface TransactionUseCase {

    AccountState transferMoney(TransactionRequest tr);
}
