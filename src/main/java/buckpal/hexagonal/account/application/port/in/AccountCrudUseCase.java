package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.member.domain.Member;

public interface AccountCrudUseCase {

    Account createAccount(Member member, int money);

}
