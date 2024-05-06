package buckpal.hexagonal.lottery.domain;

import buckpal.hexagonal.lottery.application.port.in.LotteryCrudUseCase;
import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LotteryWinnerTest {

    @Autowired
    LotteryWinnerCrudPort lotteryWinnerCrudPort;

    @Autowired
    LotteryCrudUseCase lotteryCrudUseCase;

    @Autowired
    EntityManager em;

    @Transactional
    @Test
    void 이벤트_트리거_테스트() {
        LotteryWinner byId = lotteryWinnerCrudPort.findById(1L);
        byId.updateSelected();
        em.flush();

    }

    @Transactional
    @Test
    void 업데이트후_복권_구매_실패() {
        //given
        LotteryWinner byId = lotteryWinnerCrudPort.findById(1L);
        byId.updateSelected();
        em.flush();
        CreateLotteryRequest m1_lottery = new CreateLotteryRequest(1L, "0000-0000-0");

        //when & then
        Assertions.assertThatThrownBy(()-> lotteryCrudUseCase.createLottery(m1_lottery))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The lottery winner has been selected.");

    }



}