package buckpal.hexagonal.account.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendMoneyRequest {

    private String source;
    private String destination;
    private int money;

}
