package buckpal.hexagonal.lottery.application.service.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class LotteryWinnerSelectEvent extends ApplicationEvent {

    private String message;

    public LotteryWinnerSelectEvent(Object source,String message) {
        super(source);
        this.message = message;
    }

}