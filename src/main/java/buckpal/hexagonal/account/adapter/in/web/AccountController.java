package buckpal.hexagonal.account.adapter.in.web;


import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.account.domain.dto.SendMoneyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
class AccountController {

    private final SendMoneyUseCase sendMoneyUseCase;
    private final AccountMapper accountMapper = new AccountMapper();


    @PostMapping ("/money") // 계좌 이체 Use Case
    public ClientResponse useCase(@RequestBody ClientRequest clientRequest){
        SendMoneyRequest sendMoneyRequest = accountMapper.mapToSendMoneyRequest(clientRequest);
        AccountState accountState = sendMoneyUseCase.sendMoney(sendMoneyRequest);

        return accountMapper.mapToClientResponse(accountState);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class ClientRequest{

        private String source;
        private String destination;
        private int money;
    }

    @AllArgsConstructor
    @Getter
    static class ClientResponse{
        private String name;
        private int money;
        private boolean state;
    }


}
