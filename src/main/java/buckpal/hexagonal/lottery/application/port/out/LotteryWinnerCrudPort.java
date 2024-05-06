package buckpal.hexagonal.lottery.application.port.out;

import buckpal.hexagonal.lottery.domain.LotteryWinner;

public interface LotteryWinnerCrudPort {

    LotteryWinner save(LotteryWinner lotteryWinner);
    Long getLotteryCount();
    LotteryWinner findById(Long lotteryCount);
}
