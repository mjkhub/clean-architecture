package buckpal.hexagonal.member.domain;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String transferPassword;

    private LocalDate signUpDate;

    private AtomicInteger totalMoney = new AtomicInteger();

    @OneToMany(mappedBy = "member")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Lottery> lotteryList = new ArrayList<>();


    public static Member createMember(MemberCreateRequest memberCreateRequest){
        // member 는 시스템 상에서 되게 중요한 엔티티
        // -> 시스템 상에서 동적 으로 action 을 취함 -> DDD Style 로 코딩
        Member m = new Member();
        m.name = memberCreateRequest.getName();
        m.loginId = memberCreateRequest.getLoginId();
        m.password = memberCreateRequest.getPassword();
        m.transferPassword = memberCreateRequest.getTransferPassword();
        m.signUpDate = LocalDate.now();
        return m;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
        totalMoney.addAndGet(account.getMoney());
    }


    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateTransferPassword(String transferPassword) {
        this.transferPassword = transferPassword;
    }


    public void addTotalMoney(int money){
        this.totalMoney.addAndGet(money);
    }

    public void subTotalMoney(int money){
        this.totalMoney.addAndGet(money * -1);
    }


    @Override
    public String
    toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", transferPassword='" + transferPassword + '\'' +
                ", totalMoney=" + totalMoney +
                '}';
    }



}
