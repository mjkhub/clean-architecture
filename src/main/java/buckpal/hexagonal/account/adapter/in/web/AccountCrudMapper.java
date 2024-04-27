package buckpal.hexagonal.account.adapter.in.web;


import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountCrudMapper {


    public AccountCreateRequest mapToDomainCreateRequest(AccountCrudController.CreateAccountRequest createAccountRequest){
        return new AccountCreateRequest(createAccountRequest.getName(), createAccountRequest.getPassword(), createAccountRequest.getMoney());
    }

    public AccountCrudController.CreateAccountResponse mapToCreateResponse(Account account){
        return new AccountCrudController.CreateAccountResponse(account.getNumber(),account.getMoney(),account.getSignUpDate());
    }
}
