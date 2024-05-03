package buckpal.hexagonal.lottery.application.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLotteryRequest {

    private Long MemberId;
    private String depositAccountNumber;
}
