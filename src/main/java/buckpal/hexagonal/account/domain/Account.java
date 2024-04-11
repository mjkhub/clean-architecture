package buckpal.hexagonal.account.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {

    private String name;
    private int money;


    public State transferMoney(Account targetAccount, int money){
        targetAccount.addMoney(money);
        this.subMoney(money);
        return new State(this.name, this.money);
    }

    public void addMoney(int money){
        this.money +=money;
    }
    public void subMoney(int money){
        this.money -=money;
    }

}
