package buckpal.hexagonal.account.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Account {

    private String name;
    private String password;
    private int money;


    public AccountState transferMoney(Account targetAccount, int money){
        targetAccount.addMoney(money);
        this.subMoney(money);
        return new AccountState(this.name, this.money);
    }

    public void addMoney(int money){
        this.money +=money;
    }
    public void subMoney(int money){
        this.money -=money;
    }


    public Account(String name, String password, int money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public boolean isPasswordCorrect(String name, String password){
        return this.name.equals(name) && this.password.equals(password);
    }

}
