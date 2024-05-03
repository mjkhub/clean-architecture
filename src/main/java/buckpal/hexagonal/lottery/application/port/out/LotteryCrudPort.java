package buckpal.hexagonal.lottery.application.port.out;

import buckpal.hexagonal.lottery.domain.Lottery;

public interface LotteryCrudPort {

    Lottery save(Lottery lottery);
}
