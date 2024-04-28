package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class AccountCrudController {

    private final AccountCrudUseCase accountCrudUseCase;
    private final MemberCrudUseCase memberCrudUseCase;
    private final AccountCrudMapper accountCrudMapper = new AccountCrudMapper();

    @PostMapping("/member/accounts") // create
    public CreateAccountResponse createAccount(@RequestBody AccountCreateRequest createAccountRequest, HttpServletRequest servletRequest){

        Long memberId = getMemberIdFromSession(servletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);

        Account account = accountCrudUseCase.createAccount(member, createAccountRequest);

        return new CreateAccountResponse(member.getName(),account.getNumber(), account.getMoney(),account.getSignUpDate());
    }

    @GetMapping("/member/accounts")
    public GetAccountsResponse getAccounts(HttpServletRequest servletRequest){

        Long memberId = getMemberIdFromSession(servletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);
        List<Account> accounts = accountCrudUseCase.getAccounts(member);
        List<AccountDto> accountsDto= accounts.stream()
                .map(m -> new AccountDto(m.getNumber(), m.getMoney()))
                .toList();

        return new GetAccountsResponse(accountsDto);
    }

    @GetMapping("/member/accounts/{accountNumber}") // create
    public CreateAccountResponse getAccount(HttpServletRequest servletRequest){
        // 거래 기록들을 모두 가져오는 API 가 필요함

        return null;
    }

    private static Long getMemberIdFromSession(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        Long memberId = (Long)session.getAttribute("memberId"); //could occur null pointer exception
        return memberId;
    }


    @Getter
    @AllArgsConstructor
    static class CreateAccountResponse{

        private String memberName;
        private String accountNumber;
        private int money;
        private LocalDate signUpDate;
    }


    @Getter
    @AllArgsConstructor
    static class GetAccountsResponse<T>{

        private T accounts;
    }

    @Getter
    @AllArgsConstructor
    static class AccountDto{ //자세히 보기 전 화면

        private String number;
        private int money;
    }



}
