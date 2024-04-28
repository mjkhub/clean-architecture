package buckpal.hexagonal.transaction.application.service.dto;

import buckpal.hexagonal.transaction.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionRequest {

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private int money;
    private String transactionType;


}
