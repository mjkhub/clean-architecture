package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.application.port.out.LotteryCrudPort;
import buckpal.hexagonal.lottery.domain.Lottery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LotteryAdapter implements LotteryCrudPort {

    private final LotteryJpaRepository lotteryJpaRepository;

    @Override
    public Lottery save(Lottery lottery) {
        return lotteryJpaRepository.save(lottery);
    }




}
