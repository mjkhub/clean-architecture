package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.adapter.in.web.MemberLoginController;
import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.transaction.domain.TransactionType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class AccountCrudController {

    private final AccountCrudUseCase accountCrudUseCase;
    private final MemberCrudUseCase memberCrudUseCase;
    private final SessionManager sessionManager;
    private final AccountMapper accountMapper;

    @PostMapping("/member/accounts") // create
    public CreateAccountResponse createAccount(@RequestBody AccountCreateRequest createAccountRequest, HttpServletRequest servletRequest){

        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);

        Account account = accountCrudUseCase.createAccount(member, createAccountRequest);

        return new CreateAccountResponse(member.getName(),account.getNumber(), account.getMoney(),account.getSignUpDate());
    }

    @GetMapping("/member/accounts")
    public MemberAccountsResponse getAccounts(HttpServletRequest servletRequest){

        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);

        Member member = memberCrudUseCase.findMemberById(memberId);

        List<AccountDto> accountDtoList = member.getAccounts().stream()
                .map(a -> new AccountDto(a.getNumber(), a.getMoney()))
                .toList();
        return new MemberAccountsResponse(member.getName(), member.getTotalMoney().get(), accountDtoList); // Account 객체를 또 dto 로 변환 해야 함
    }

    @GetMapping("/member/accounts/{accountNumber}")
    public AccountDtoWithTransactions getAccount(HttpServletRequest servletRequest,
                                                 @PathVariable String accountNumber,
                                                 @RequestParam(required = false,name="type") String transactionType){


        // account 와 더불어, 연관된 Transaction 내역 까지 모두 조회하는 API 가 필요함
        Long memberId = sessionManager.getMemberIdFromSession(servletRequest);
        Account accountWithTransactions = accountCrudUseCase.getAccountWithTransactions(memberId, accountNumber,transactionType);

        return accountMapper.mapToDto(accountWithTransactions);
        // fetch join으로 쿼리 한방에 해결 완료
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
    @NoArgsConstructor
    static class MemberAccountsResponse {

        private String name;
        private int totalMoney;
        private List<AccountDto> accounts;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class AccountDto{
        private String accountNumber;
        private int money;
    }

    @Getter
    @AllArgsConstructor
    static class AccountDtoWithTransactions{

        private String number;
        private int money;

        List<TransactionByDate> transactionByDates;

    }


}
