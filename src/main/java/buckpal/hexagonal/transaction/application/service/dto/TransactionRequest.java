package buckpal.hexagonal.transaction.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionRequest {

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private int amount;

}
