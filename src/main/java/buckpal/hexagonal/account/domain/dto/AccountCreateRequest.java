package buckpal.hexagonal.account.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AccountCreateRequest {

    private String bankName;
    private int money;

}
