package buckpal.hexagonal;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestEventPublisher {

    private final ApplicationEventPublisher publisher;
    @Scheduled(cron = "0 0 12 * * ?")
    public void publicEvent(){
        publisher.publishEvent("event from " + this.getClass());
    }
}
