package buckpal.hexagonal.transaction.domain;

import buckpal.hexagonal.account.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Transaction {

    @Id @GeneratedValue
    @Column(name="transaction_id")
    private Long id;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private int money;
    private TransactionType transactionType;

    private LocalDateTime transactionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    private String counterPartName;
    private int accountMoneyAfterTransaction;


    public Transaction(String sourceAccountNumber, String destinationAccountNumber, int money, TransactionType transactionType, LocalDateTime transactionTime, Account account, String counterPartName, int accountMoneyAfterTransaction) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.money = money;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
        this.account = account;
        this.counterPartName = counterPartName;
        this.accountMoneyAfterTransaction = accountMoneyAfterTransaction;

        // account 에 거래 등록
        account.getTransactions().add(this);
    }
}
