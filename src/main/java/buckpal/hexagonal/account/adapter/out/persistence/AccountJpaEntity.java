package buckpal.hexagonal.account.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class AccountJpaEntity {

    @Id @GeneratedValue
    @Column(name="account_id")
    private Long id;

    private String name;
    private String password;
    private int money;


    public AccountJpaEntity(String name, String password, int money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public void updateMoney(int money){
        this.money = money;
    }
}
