package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.AccountCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
class AccountCrudController {

    private final AccountCrudUseCase accountCrudUseCase;

    private final AccountCrudMapper accountCrudMapper = new AccountCrudMapper();

    @PostMapping("/accounts") // create
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        AccountCreateRequest accountCreateRequest = accountCrudMapper.mapToDomainCreateRequest(createAccountRequest);
        Account account = accountCrudUseCase.createAccount(accountCreateRequest);
        return accountCrudMapper.mapToCreateResponse(account);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateAccountRequest{
        private String name;
        private String password;
        private int money;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateAccountResponse{
        private String name;
        private int money;
        private LocalDate signUpDate;
    }
}
