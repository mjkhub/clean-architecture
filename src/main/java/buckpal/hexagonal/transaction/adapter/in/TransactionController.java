package buckpal.hexagonal.transaction.adapter.in;
import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.transaction.application.port.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;
    private final SessionManager sessionManager;

    @PostMapping("/member/accounts/{accountNumber}/transactions") // 계좌 이체 Use Case
    public AccountState transferMoney(HttpServletRequest httpServletRequest, @RequestBody DestinationInfo di, @PathVariable String accountNumber){

        Long memberId = sessionManager.getMemberIdFromSession(httpServletRequest);
        TransactionRequest tr = mapToTransactionRequest(accountNumber, di);
        AccountState accountState = transactionUseCase.transferMoney(tr);

        return accountState;
    } //반환 스펙 괜찮은가

    private TransactionRequest mapToTransactionRequest(String accountNumber, DestinationInfo di){
        return new TransactionRequest(accountNumber, di.getDestinationAccountNumber(),di.getMoney(),di.getTransactionType());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class DestinationInfo{
        private String destinationAccountNumber;
        private int money;
        private String transactionType;


    }

}
