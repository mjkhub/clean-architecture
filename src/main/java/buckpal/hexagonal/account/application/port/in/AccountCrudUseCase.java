package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountCreateRequest;

public interface AccountCrudUseCase {

    Account createAccount(AccountCreateRequest accountCreateRequest);


}
