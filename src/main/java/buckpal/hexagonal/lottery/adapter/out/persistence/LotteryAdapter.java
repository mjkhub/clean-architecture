package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.application.port.out.LotteryCrudPort;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class LotteryAdapter implements LotteryCrudPort {

    private final LotteryJpaRepository lotteryJpaRepository;

    @Override
    public Lottery save(Lottery lottery) {
        return lotteryJpaRepository.save(lottery);
    }


    @Override
    public boolean hasBought(Member member, Long lotteryCount) {
        return lotteryJpaRepository.findLotteryWithMember(member, lotteryCount).isPresent();
    }

    @Override
    public List<Lottery> findAllLottery(Long lotteryCount) {
        return lotteryJpaRepository.findAllLottery(lotteryCount);
    }

    @Override
    public Lottery findLottery(Member member, Long lotteryCount) {
        return lotteryJpaRepository.findLotteryWithMember(member, lotteryCount).orElseThrow();
    }
}
