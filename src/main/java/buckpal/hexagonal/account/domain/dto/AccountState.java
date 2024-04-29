package buckpal.hexagonal.account.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountState {

    private String destinationAccountNumber;
    private int transferMoney;
    private int moneyAfterTransaction;

}
