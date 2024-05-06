package buckpal.hexagonal.lottery.application.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LotteryData {


    private AtomicLong lotteryCount = new AtomicLong(); //현재 몇 회차냐
    private AtomicInteger buyerCount = new AtomicInteger(); // 구매한 사람들 명수
    private List<String> lotteryNumbers = new ArrayList<>(); // 해당 회차에 입력한 로또 번호들 저장
                                                            //엄밀히 따지자면 db에 조회된 내용을 바탕으로 해야하는데 말이지

    public void initLotteryCount(Long lotteryCount) {
        this.lotteryCount.set(lotteryCount);
    }

    public void initBuyerCount(Integer buyerCount) {
        this.buyerCount.set(buyerCount);
    }

    public void initLotteryNumbers(List<String> lotteryNumbers) {
        this.lotteryNumbers = lotteryNumbers;
    }

    public Long getLotteryCount() {
        return lotteryCount.get();
    }
    public void incrementLotteryCount(){
        lotteryCount.incrementAndGet();
    }

    public Integer getBuyerCount(){
        return buyerCount.get();
    }

    public void incrementLotteryBuyers(){
        buyerCount.incrementAndGet();
    }

    public void addLotteryNumber(String lotteryNumber){
        lotteryNumbers.add(lotteryNumber);
    }

    public List<String> getLotteryNumbers() {
        return lotteryNumbers;
    }
}
