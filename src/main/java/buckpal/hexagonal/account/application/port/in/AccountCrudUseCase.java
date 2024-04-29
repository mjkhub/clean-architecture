package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.domain.Member;

import java.util.List;

public interface AccountCrudUseCase {

    Account createAccount(Member member, AccountCreateRequest accountCreateRequest);

    List<Account> getAccountsOfMember(Long memberId);

    Account getAccountWithTransactions(Long memberId, String accountNumber);
}
