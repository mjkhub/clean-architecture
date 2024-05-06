package buckpal.hexagonal.lottery.domain;

import buckpal.hexagonal.lottery.application.service.event.LotteryWinnerSelectEventPublisher;
import jakarta.persistence.*;
import lombok.*;

@Getter @Entity
@EntityListeners(LotteryWinnerSelectEventPublisher.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LotteryWinner {


    @Id @GeneratedValue
    @Column(name="lottery_count")
    private Long lotteryCount;

    @Setter
    private int lotteryBuyers;

    @Setter
    private int winnerMoney;

    @Setter
    private String winnerLotteryNumber;

    @Setter
    private String winnerAccountNumber;

    private boolean selected = false;

    public void updateSelected(){
        if(selected)
            throw new IllegalStateException("The lottery winner has been selected");
        this.selected = true;
    }

    @Override
    public String toString() {
        return "LotteryWinner{" +
                "lotteryCount=" + lotteryCount +
                ", lotteryBuyers=" + lotteryBuyers +
                ", winnerMoney=" + winnerMoney +
                ", winnerLotteryNumber='" + winnerLotteryNumber + '\'' +
                ", winnerAccountNumber='" + winnerAccountNumber + '\'' +
                ", selected=" + selected +
                '}';
    }
}
