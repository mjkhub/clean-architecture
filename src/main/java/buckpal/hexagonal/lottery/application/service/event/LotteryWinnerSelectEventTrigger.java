package buckpal.hexagonal.lottery.application.service.event;


import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.application.service.LotteryData;
import buckpal.hexagonal.lottery.domain.LotteryWinner;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LotteryWinnerSelectEventTrigger {

    private final LotteryWinnerCrudPort lotteryWinnerCrudPort;

    private final LotteryData lotteryData;
    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void publicEvent(){
        LotteryWinner lw = lotteryWinnerCrudPort.findById(lotteryData.getLotteryCount());
        lw.updateSelected(); // trigger LotteryWinnerSelectEvent
    }



}
