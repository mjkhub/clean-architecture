package buckpal.hexagonal.account.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class AccountJpaEntity {

    @Id @GeneratedValue
    @Column(name="account_id")
    private Long id;

    @Column(unique = true) //
    private String name;
    private String password;
    private int money;
    private LocalDate signUpDate;


    public AccountJpaEntity(String name, String password, int money, LocalDate signUpDate) {
        this.name = name;
        this.password = password;
        this.money = money;
        this.signUpDate = signUpDate;
    }

    public void updateMoney(int money){
        this.money = money;
    }
}
