package buckpal.hexagonal.lottery.application.service;


import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.lottery.application.port.in.LotteryCrudUseCase;
import buckpal.hexagonal.lottery.application.port.out.LotteryCrudPort;
import buckpal.hexagonal.lottery.application.port.out.LotteryWinnerCrudPort;
import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.lottery.domain.LotteryConst;
import buckpal.hexagonal.lottery.domain.LotteryWinner;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
class LotteryCreateService implements LotteryCrudUseCase {

    private final LotteryCrudPort lotteryCrudPort;

    private final LotteryWinnerCrudPort lotteryWinnerCrudPort;

    private final MemberCrudPort memberCrudPort;

    private final AccountCrudPort accountCrudPort;

    private final LotteryData lotteryData;

    @Transactional
    public Lottery createLottery(CreateLotteryRequest createLotteryRequest){

        Member member = memberCrudPort.findById(createLotteryRequest.getMemberId()).orElseThrow();
        //이 계좌 를 이용 해서 복권을 구매한 기록도 남기고 복권에 당첨시 입금 계좌로 사용
        String depositAccountNumber = createLotteryRequest.getDepositAccountNumber();

        Account userAccount = accountCrudPort.findByAccountNumber(depositAccountNumber);
        Account lotteryManagerAccount = accountCrudPort.findByAccountNumber(LotteryConst.LOTTERY_MANAGER_ACCOUNT_NUMBER);
        userAccount.transferMoney(lotteryManagerAccount, LotteryConst.LOTTERY_PRICE);

        return generateLottery(member, depositAccountNumber);
    }

    private Lottery generateLottery(Member member, String depositAccountNumber) {

        // 각 회차에 멤버는 복권을 한장만 구매할 수 있음
        if(lotteryCrudPort.hasBought(member, lotteryData.getLotteryCount()))
            throw new IllegalStateException("You've already bought a lottery on this season");

        String lotteryNumber = generateLotteryNumber();
        Lottery lottery = new Lottery(lotteryData.getLotteryCount(), lotteryNumber, member, depositAccountNumber);

        lotteryCrudPort.save(lottery);
        checkLotteryPurchaseAvailable();

        lotteryData.addLotteryNumber(lotteryNumber);
        lotteryData.incrementLotteryBuyers();
        return lottery;
    }

    private void checkLotteryPurchaseAvailable(){
        LotteryWinner lotteryWinner = lotteryWinnerCrudPort.findById(lotteryData.getLotteryCount());
        if(lotteryWinner.isSelected())
            throw new IllegalStateException("The lottery winner has been selected.");
    }

    private String generateLotteryNumber(){
        SecureRandom secureRandom = new SecureRandom(); // SecureRandom 객체 생성
        StringBuilder result = new StringBuilder(); // 결과를 저장할 StringBuilder 객체

        for (int i = 0; i < 10; i++) {
            int number = secureRandom.nextInt(10); // 0~9 사이의 랜덤 숫자 생성
            result.append(number); // StringBuilder 에 숫자 추가
        }

        if(lotteryData.getLotteryNumbers().contains(result.toString())) return generateLotteryNumber();
        return result.toString();
    }





}
