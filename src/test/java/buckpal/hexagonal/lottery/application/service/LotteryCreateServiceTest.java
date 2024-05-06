package buckpal.hexagonal.lottery.application.service;

import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class LotteryCreateServiceTest {

    @Autowired
    LotteryCreateService lotteryCreateService;

    @Autowired
    MemberCrudPort memberCrudPort;

    @Transactional
    @Test
    void createLottery() { // test 할때, initializer 의 초기 데이터 삭제

        //given
        String depositAccountNumber = "0000-0000-0";
        long lotteryManagerId = 1L;
        long memberId = 2L;
        Member member = memberCrudPort.findById(memberId).orElseThrow();
        int moneyBeforeBuyingALottery = member.getTotalMoney().get();
        CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest(memberId, depositAccountNumber);

        //when
        Lottery lottery = lotteryCreateService.createLottery(createLotteryRequest);


        //then
        assertThat(lottery.getDepositAccountNumber()).isEqualTo(depositAccountNumber);

        assertThat(moneyBeforeBuyingALottery - 1000).isEqualTo(member.getTotalMoney().get());

        assertThat(memberCrudPort.findById(lotteryManagerId).orElseThrow().getTotalMoney().get()).isEqualTo(1000);

        //조금 더 상세한 테스트 가 필요하나 쿼리로 확인했고 이 정도만 쓰자.


    }


    @Transactional
    @Test
    public void 동일_회차_로또_재구매() throws Exception{
        //given
        String depositAccountNumber = "0000-0000-0";
        long lotteryManagerId = 1L;
        long memberId = 2L;
        Member member = memberCrudPort.findById(memberId).orElseThrow();
        int moneyBeforeBuyingALottery = member.getTotalMoney().get();
        CreateLotteryRequest createLotteryRequest = new CreateLotteryRequest(memberId, depositAccountNumber);

        //when
        Lottery lottery = lotteryCreateService.createLottery(createLotteryRequest);

        //then
        assertThatThrownBy(()-> lotteryCreateService.createLottery(createLotteryRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("You've already bought a lottery on this season");

    }
}