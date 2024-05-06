package buckpal.hexagonal.lottery.application.port.in;

import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import buckpal.hexagonal.lottery.domain.Lottery;

public interface LotteryCrudUseCase {

    Lottery createLottery(CreateLotteryRequest createLotteryRequest);
}
