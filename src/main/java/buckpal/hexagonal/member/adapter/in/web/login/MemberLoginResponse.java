package buckpal.hexagonal.member.adapter.in.web.login;

import buckpal.hexagonal.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class MemberLoginResponse {

    private String name;

    private int totalMoney;

    private List<Account> accounts;

}
