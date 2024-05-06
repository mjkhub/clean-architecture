package buckpal.hexagonal.lottery.domain;

import buckpal.hexagonal.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lottery {

    @Id @GeneratedValue
    @Column(name="lottery_purchase_id")
    private Long id;

    private Long lotteryCount;

    private String lotteryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String depositAccountNumber;

    public Lottery(Long lotteryCount, String lotteryNumber, Member member, String depositAccountNumber) {

        // Lottery 는 시스템 에서 정적인 엔티티
        // 생성 된후 바뀌질 않음. 거래도 마찬 가지 그래서 이러한 스타일 로 코딩
        this.lotteryCount = lotteryCount;
        this.lotteryNumber = lotteryNumber;
        this.member = member;
        member.getLotteryList().add(this);
        this.depositAccountNumber = depositAccountNumber;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id=" + id +
                ", lotteryCount=" + lotteryCount +
                ", lotteryNumber='" + lotteryNumber + '\'' +
                ", member=" + member.getName() +
                ", depositAccountNumber='" + depositAccountNumber + '\'' +
                '}';
    }
}
