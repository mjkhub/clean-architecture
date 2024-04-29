package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class AccountCrudController {

    private final AccountCrudUseCase accountCrudUseCase;
    private final MemberCrudUseCase memberCrudUseCase;
    private final SessionManager sessionManager;

    @PostMapping("/member/accounts") // create
    public CreateAccountResponse createAccount(@RequestBody AccountCreateRequest createAccountRequest, HttpServletRequest servletRequest){

        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);

        Account account = accountCrudUseCase.createAccount(member, createAccountRequest);

        return new CreateAccountResponse(member.getName(),account.getNumber(), account.getMoney(),account.getSignUpDate());
    }

    @GetMapping("/member/accounts")
    public GetAccountsResponse getAccounts(HttpServletRequest servletRequest){

        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);

        List<Account> accounts = accountCrudUseCase.getAccountsOfMember(memberId);
        List<AccountDto> accountsDto = accounts.stream()
                .map(m -> new AccountDto(m.getNumber(), m.getMoney()))
                .toList();

        return new GetAccountsResponse(accountsDto);
    }

    @GetMapping("/member/accounts/{accountNumber}")
    public CreateAccountResponse getAccount(HttpServletRequest servletRequest, @PathVariable String accountNumber){

        // account 와 더불어, 연관된 Transaction 내역 까지 모두 조회하는 API 가 필요함
        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);
        Account accountWithTransactions = accountCrudUseCase.getAccountWithTransactions(memberId, accountNumber);

        // 이제 API 의 스펙에 맞게
        // 날짜 순으로 정렬, 날짜 순으로 구분
        return null;
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
