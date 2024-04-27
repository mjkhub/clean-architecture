package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final MemberCrudUseCase memberCrudUseCase;
    private final AccountCrudMapper accountCrudMapper = new AccountCrudMapper();

    @PostMapping("/account") // create
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest, HttpServletRequest servletRequest){
        AccountCreateRequest accountCreateRequest = accountCrudMapper.mapToDomainCreateRequest(createAccountRequest);

        HttpSession session = servletRequest.getSession(false);
        Long memberId = (Long)session.getAttribute("memberId");
        //member 가져오기

        System.out.println("memberId = " + memberId);
        Account account = accountCrudUseCase.createAccount(null, createAccountRequest.getMoney());

        return new CreateAccountResponse("",account.getNumber(), account.getMoney(),account.getSignUpDate());
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateAccountRequest{
        private int money;
    }

    @Getter
    @AllArgsConstructor
    static class CreateAccountResponse{

        private String memberName;
        private String accountNumber;
        private int money;
        private LocalDate signUpDate;
    }
}
