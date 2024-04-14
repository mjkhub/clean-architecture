package buckpal.hexagonal.account.domain;

import lombok.Getter;

@Getter
public class Account {

    private String name;
    private String password;
    private int money;

    public Account(String name, String password, int money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public AccountState transferMoney(Account targetAccount, int money){
        if(isSufficientMoney(money)){
            targetAccount.addMoney(money);
            this.subMoney(money);
            return new AccountState(this.name, this.money);
        }else{
            throw new IllegalArgumentException("Wrong input");
        }

    }

    public boolean isPasswordCorrect(String name, String password){
        return this.name.equals(name) && this.password.equals(password);
    }

    private void addMoney(int money){
        this.money +=money;
    }
    private void subMoney(int money){
        this.money -=money;
    }

    private boolean isSufficientMoney(int money){
        return this.money >= money;
    }


}
