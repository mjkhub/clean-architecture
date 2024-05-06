package buckpal.hexagonal;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

    @EventListener
    public void handleStringEvent(String message) {
        System.out.println("Received string event: " + message);
    }


}
