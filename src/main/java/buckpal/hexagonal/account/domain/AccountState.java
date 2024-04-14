package buckpal.hexagonal.account.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountState {

    private String name;
    private int money;

}
