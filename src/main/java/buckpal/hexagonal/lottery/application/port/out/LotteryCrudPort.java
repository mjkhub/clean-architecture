package buckpal.hexagonal.lottery.application.port.out;

import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.member.domain.Member;

import java.util.List;

public interface LotteryCrudPort {

    Lottery save(Lottery lottery);
    boolean hasBought(Member member, Long lotteryCount);

    List<Lottery> findAllLottery(Long lotteryCount);

    Lottery findLottery(Member member, Long lotteryCount);

}
