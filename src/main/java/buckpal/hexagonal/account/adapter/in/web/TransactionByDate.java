package buckpal.hexagonal.account.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TransactionByDate{

    private String transactionDate;
    private List<TransactionDto> transactions;

    @Getter
    @AllArgsConstructor
    static class TransactionDto{
        private String transactionTime;
        private String transactionType;
        private String counterPartName;
        private int accountMoneyAfterTransaction;
    }
}
