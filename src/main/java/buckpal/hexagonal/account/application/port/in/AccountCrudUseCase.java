package buckpal.hexagonal.account.application.port.in;

import buckpal.hexagonal.account.domain.Account;


public interface AccountCrudUseCase {

    Account createAccount();

}
