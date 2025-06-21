package buckpal.hexagonal.transaction.application.port.in;

import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;

public interface TransactionUseCase {

    int transferMoney(TransactionRequest tr);
}
