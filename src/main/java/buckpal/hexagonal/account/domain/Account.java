package buckpal.hexagonal.account.domain;

import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.transaction.domain.Transaction;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {


    @Id @GeneratedValue
    @Column(name="account_id")
    private Long id;

    @Column(unique = true)
    private String accountNumber; // 계좌 번호
    private String transferPassword; // 송금시 입력할 비밀 번호
    private int money;
    private LocalDate signUpDate;

    @Enumerated(EnumType.STRING)
    private BankName bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    public static Account createAccount(String accountNumber, Member member, AccountCreateRequest accountCreateRequest){
        Account account = new Account();
        account.accountNumber = accountNumber;
        account.transferPassword = member.getTransferPassword();
        account.money = accountCreateRequest.getMoney();
        account.signUpDate = LocalDate.now();
        account.bankName = BankName.getBankType(accountCreateRequest.getBankName());
        account.member = member;
        member.addAccount(account);
        return account;
    }

    //이게 객체를 분리하는게 진짜 에바임.
    public AccountState transferMoney(Account destinationAccount, int money){

        // 돈을 보낸 account 의 정보 변경
        // 1. 계좌의 금액  2. 멤버가 가진 돈
        this.subMoney(money);
        this.member.subTotalMoney(money);

        // 돈을 받은 account 정보 변경
        // 1. 계좌의 금액  2. 멤버가 가진 돈
        destinationAccount.addMoney(money);
        destinationAccount.getMember().addTotalMoney(money);

        return new AccountState(destinationAccount.getAccountNumber(), money, this.getMoney());
    }


    public boolean isNameCorrect(String name){
        return this.accountNumber.equals(name);
    }
    public boolean isPasswordCorrect(String password){
        return this.transferPassword.equals(password);
    }

    private void addMoney(int money){
        this.money +=money;
    }
    private void subMoney(int money){
        if(this.money < money)
            throw new IllegalArgumentException("Account money is smaller than input money");
        this.money -=money;
    }

}
