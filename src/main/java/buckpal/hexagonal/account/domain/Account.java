package buckpal.hexagonal.account.domain;

import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {


    @Id @GeneratedValue
    @Column(name="account_id")
    private Long id;

    @Column(unique = true)
    private String number; // 계좌 번호
    private String transferPassword; // 송금시 입력할 비밀 번호
    private int money;
    private LocalDate signUpDate;

    @Enumerated(EnumType.STRING)
    private BankName bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //거래내역들

    public static Account createAccount(String accountNumber, Member member, AccountCreateRequest accountCreateRequest){
        Account account = new Account();
        account.number = accountNumber;
        account.transferPassword = member.getTransferPassword();
        account.money = accountCreateRequest.getMoney();
        account.signUpDate = LocalDate.now();
        account.bankName = BankName.getBankType(accountCreateRequest.getBankName());
        account.member = member;
        member.addAccount(account);
        return account;
    }

    //이게 객체를 분리하는게 진짜 에바임.
    public AccountState transferMoney(Account targetAccount, int money){
        if(isSufficientMoney(money)){
            targetAccount.addMoney(money);
            this.subMoney(money);
            return new AccountState(this.number, this.money);
        }else{
            throw new IllegalArgumentException("Wrong input");
        }
    }

    public boolean isNameCorrect(String name){
        return this.number.equals(name);
    }
    public boolean isPasswordCorrect(String password){
        return this.transferPassword.equals(password);
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


    public void updateMoney(int money) {
        //로직은 좀 생각해 봐야 할듯
    }
}
