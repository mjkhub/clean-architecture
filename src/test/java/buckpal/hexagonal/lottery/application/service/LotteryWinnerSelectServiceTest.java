package buckpal.hexagonal.lottery.application.service;

import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.domain.LotteryWinner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LotteryWinnerSelectServiceTest {

    @Autowired
    LotteryWinnerSelectService selectService;

    @Autowired
    LotteryWinnerCrudPort lotteryWinnerCrudPort;


    @Test
    void selectLotteryWinner() {

        //이 메소드에 event Listener 가 없을 때 테스트

        LotteryWinner lotteryWinner = selectService.selectLotteryWinner(null);

        System.out.println("lotteryWinner = " + lotteryWinner);

        System.out.println("lotteryWinner2= " + lotteryWinnerCrudPort.findById(2L));

    }
}