package buckpal.hexagonal.lottery.application.service.event;

import buckpal.hexagonal.lottery.domain.LotteryWinner;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class LotteryWinnerSelectEventPublisher {

    private final ApplicationEventPublisher publisher;

    @PostUpdate
    public void onLotteryWinnerSelect(LotteryWinner lotteryWinner){
        if(lotteryWinner.isSelected() && lotteryWinner.getWinnerLotteryNumber() == null)
            publisher.publishEvent(new LotteryWinnerSelectEvent(this,"Select lottery winner"));
    }

}
