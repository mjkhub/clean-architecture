package buckpal.hexagonal.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AccountCreateRequest {

    private String name;
    private String password;
    private int money;

}
