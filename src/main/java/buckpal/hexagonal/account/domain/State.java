package buckpal.hexagonal.account.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class State {

    private String name;
    private int money;
}
