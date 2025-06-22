package buckpal.hexagonal.account.adapter.in.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import buckpal.hexagonal.account.adapter.in.web.api.AccountResponse;
import buckpal.hexagonal.account.application.port.in.AccountCrudUseCase;
import buckpal.hexagonal.account.domain.Account;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "계좌정보 조회")
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {


    private final AccountCrudUseCase accountCrudUseCase;

    @Operation(
        summary = "계좌 상세 조회",
        description = "계좌 ID를 이용하여 계좌 번호와 잔액 정보를 조회합니다."
    )
    @GetMapping("/{account-id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("account-id") Long accountId){
        Account account = accountCrudUseCase.findByAccountId(accountId);
        return ResponseEntity.ok(new AccountResponse(account.getAccountNumber(), account.getBalance()));
    }
}
