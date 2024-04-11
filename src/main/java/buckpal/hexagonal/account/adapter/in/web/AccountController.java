package buckpal.hexagonal.account.adapter.in.web;


import buckpal.hexagonal.account.application.port.in.SendMoneyUseCase;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import buckpal.hexagonal.account.domain.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AccountController {

    private final SendMoneyUseCase sendMoneyUseCase;

    private final AccountMapper accountMapper = new AccountMapper();

    @PostMapping ("/money")
    public ClientResponse useCase(@RequestBody ClientRequest clientRequest){

        SendMoneyRequest sendMoneyRequest = accountMapper.clientRequestToAccount(clientRequest);
        State state = sendMoneyUseCase.sendMoney(sendMoneyRequest);
        ClientResponse clientResponse = accountMapper.stateToClientResponse(state);
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
