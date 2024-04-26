package buckpal.hexagonal.account.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private String name;
    private String password;
    private int money;
    private LocalDate signUpDate;

    public static Account createAccount(String name, String password, int money){
        Account account = new Account();
        account.name = name;
        account.password = password;
        account.money = money;
        account.signUpDate = LocalDate.now();
        return account;
    }

    //이게 객체를 분리하는게 진짜 에바임.

    public AccountState transferMoney(Account targetAccount, int money){
        if(isSufficientMoney(money)){
            targetAccount.addMoney(money);
            this.subMoney(money);
            return new AccountState(this.name, this.money);
        }else{
            throw new IllegalArgumentException("Wrong input");
        }
    }

    public boolean isNameCorrect(String name){
        return this.name.equals(name);
    }
    public boolean isPasswordCorrect(String password){
        return this.password.equals(password);
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
