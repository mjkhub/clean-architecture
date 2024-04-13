package buckpal.hexagonal.account.adapter.in.web;


import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.domain.AccountState;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class AccountController {

    private final SendMoneyUseCase sendMoneyUseCase;
    private final AccountMapper accountMapper = new AccountMapper();

//    private final ObjectMapper objectMapper = new ObjectMapper();


//    @PostMapping("/accounts/{accountId}")
//    public Account loginAccount(@PathVariable String accountId, @RequestBody String passwordJson) throws JsonProcessingException {
//
//        String password = objectMapper.readTree(passwordJson).get("password").asText();
//        accountCrudUseCase.
//
//        return null;
//    }


    @PostMapping ("/money") // 계좌 이체 UseCase
    public ClientResponse useCase(@RequestBody ClientRequest clientRequest){

        SendMoneyRequest sendMoneyRequest = accountMapper.mapToSendMoneyRequest(clientRequest);
        AccountState accountState = sendMoneyUseCase.sendMoney(sendMoneyRequest);
        ClientResponse clientResponse = accountMapper.mapToClientResponse(accountState);
        return clientResponse;
    }



    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class ClientRequest{

        private String source;
        private String destination;
        private int money;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class ClientResponse{
        private String name;
        private int money;
        private boolean state;
    }


}
