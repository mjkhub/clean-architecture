package buckpal.hexagonal.account.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long id;

    @Column(unique = true)
    private String accountNumber;
    private int balance;
    private LocalDate signUpDate;

    public void addMoney(int amount){
        this.balance += amount;
    }

    public void subMoney(int amount){
        if(this.balance < amount)
            throw new IllegalArgumentException("Account balance is smaller than amount");
        this.balance -= amount;
    }

}
