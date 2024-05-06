package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.domain.Lottery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LotteryJpaRepositoryTest {

    @Autowired
    LotteryJpaRepository lotteryJpaRepository;


    @Test
    @Transactional
    void findAllLottery() {

        List<Lottery> allLottery = lotteryJpaRepository.findAllLottery(1L);

        assertThat(allLottery.size()).isEqualTo(2);
        System.out.println("allLottery.get(0) = " + allLottery.get(0));
        System.out.println("allLottery.get(0) = " + allLottery.get(1));
    }
}