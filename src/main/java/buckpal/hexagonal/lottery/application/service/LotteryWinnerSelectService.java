package buckpal.hexagonal.lottery.application.service;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.lottery.application.port.out.LotteryCrudPort;
import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.application.service.event.LotteryWinnerSelectEvent;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.lottery.domain.LotteryConst;
import buckpal.hexagonal.lottery.domain.LotteryWinner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
class LotteryWinnerSelectService {

    private final LotteryCrudPort lotteryCrudPort;
    private final LotteryWinnerCrudPort lotteryWinnerCrudPort;
    private final AccountCrudPort accountCrudPort;

    private final LotteryData lotteryData;

    @Transactional
    @EventListener
    public LotteryWinner selectLotteryWinner(LotteryWinnerSelectEvent lotteryWinnerSelectEvent){


        // 그리고 그 값을 바탕으로 service의 lottery count를 업데이트 시켜줌.

        // 그리고 매번 Lottery winner 를 select 할 때마다..

        // 1. write 를 하지 못하게 한다. 중요한 기능

        // 2. winner 를 뽑아서 lottery winner 엔티티 update

        // 3. 송금 해주기

        // 4. service 의 lottery count 를 update 하자 .

        Long lotteryCount = lotteryData.getLotteryCount();
        List<Lottery> allLottery = lotteryCrudPort.findAllLottery(lotteryCount);

        System.out.println("allLottery = " + allLottery.size());

        int randomLotteryIndex = getRandomLotteryIndex(allLottery.size());
        Lottery selectedlottery = allLottery.get(randomLotteryIndex); // 당첨된 복권

        // winner 에게 상금 송금 logic
        String winnerAccountNumber = selectedlottery.getDepositAccountNumber();
        Account managerAccount = accountCrudPort.findByAccountNumber(LotteryConst.LOTTERY_MANAGER_ACCOUNT_NUMBER);
        Account winnerAccount = accountCrudPort.findByAccountNumber(winnerAccountNumber); // 복권 구매 계좌

        int winnerMoney = lotteryData.getBuyerCount() * LotteryConst.LOTTERY_PRICE;
        managerAccount.transferMoney(winnerAccount, winnerMoney);

        // 해당 회차 lottery winner 정보 update logic
        LotteryWinner lotteryWinner = lotteryWinnerCrudPort.findById(lotteryData.getLotteryCount());
        lotteryWinner.setLotteryBuyers(lotteryData.getBuyerCount());
        lotteryWinner.setWinnerMoney(winnerMoney);
        lotteryWinner.setWinnerLotteryNumber(selectedlottery.getLotteryNumber());
        lotteryWinner.setWinnerAccountNumber(winnerAccountNumber);

        //lotteryData clear
        lotteryData.initLotteryNumbers(new ArrayList<String>());
        lotteryData.initBuyerCount(0);
        lotteryData.incrementLotteryCount();

        lotteryWinnerCrudPort.save(new LotteryWinner());

        return lotteryWinner;
    }

    private int getRandomLotteryIndex(int size){
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(size);
    }




}
