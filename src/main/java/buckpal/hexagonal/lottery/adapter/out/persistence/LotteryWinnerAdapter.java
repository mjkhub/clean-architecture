package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.domain.LotteryWinner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
class LotteryWinnerAdapter implements LotteryWinnerCrudPort {

    private final LotteryWinnerJpaRepository lotteryWinnerJpaRepository;

    @Transactional // for init case
    @Override
    public LotteryWinner save(LotteryWinner lotteryWinner) {
        return lotteryWinnerJpaRepository.save(lotteryWinner);
    }

    @Override
    public Long getLotteryCount() {
        return lotteryWinnerJpaRepository.getEntityNumber();
    }

    @Override
    public LotteryWinner findById(Long lotteryCount) {
        return lotteryWinnerJpaRepository.findById(lotteryCount).orElseThrow();
    }


}
