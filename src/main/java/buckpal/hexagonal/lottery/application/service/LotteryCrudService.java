package buckpal.hexagonal.lottery.application.service;


import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.lottery.application.port.out.LotteryCrudPort;
import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.lottery.domain.LotteryConst;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
class LotteryCrudService {

    private final LotteryCrudPort lotteryCrudPort;

    private final MemberCrudPort memberCrudPort;

    private final AccountCrudPort accountCrudPort;

    private final int lotteryPrice = 1000;
    private AtomicInteger lotteryCount = new AtomicInteger(1); //회차
    private AtomicInteger buyerCount = new AtomicInteger(); // 구매한 사람들 명수
    private List<String> lotteryNumbers = new ArrayList<>();


    @Transactional
    public Lottery createLottery(CreateLotteryRequest createLotteryRequest){
        Member member = memberCrudPort.findById(createLotteryRequest.getMemberId()).orElseThrow();
        //이 계좌 를 이용 해서 복권을 구매한 기록도 남기고 복권에 당첨시 입금도 하도록 설계
        String depositAccountNumber = createLotteryRequest.getDepositAccountNumber();
        Account userAccount = accountCrudPort.findByAccountNumber(depositAccountNumber);
        Account lotteryManagerAccount = accountCrudPort.findByAccountNumber(LotteryConst.LOTTERY_MANAGER_ACCOUNT_NUMBER);

        Lottery lottery = new Lottery(lotteryCount.get(), generateLotteryNumber(), member, depositAccountNumber);
        lotteryCrudPort.save(lottery);
        buyerCount.incrementAndGet();

        userAccount.transferMoney(lotteryManagerAccount, lotteryPrice);

        return lottery;
    }


    private String generateLotteryNumber(){
        SecureRandom secureRandom = new SecureRandom(); // SecureRandom 객체 생성
        StringBuilder result = new StringBuilder(); // 결과를 저장할 StringBuilder 객체

        for (int i = 0; i < 10; i++) {
            int number = secureRandom.nextInt(10); // 0~9 사이의 랜덤 숫자 생성
            result.append(number); // StringBuilder에 숫자 추가
        }

        if(lotteryNumbers.contains(result.toString())) return generateLotteryNumber();
        return result.toString();
    }



    public void emptyLotteryNumbers(){
        this.lotteryNumbers = new ArrayList<>();
    }




}
