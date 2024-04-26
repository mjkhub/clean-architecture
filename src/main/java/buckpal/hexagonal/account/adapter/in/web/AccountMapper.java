package buckpal.hexagonal.account.adapter.in.web;

import buckpal.hexagonal.account.domain.AccountCreateRequest;
import buckpal.hexagonal.account.domain.AccountState;
import buckpal.hexagonal.account.domain.SendMoneyRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class AccountMapper { //Client 의 request 스펙을 서비스 인터페이스 매개변수로 변환 * 여기서 몇몇 도메인 접근 가능

    public SendMoneyRequest mapToSendMoneyRequest(AccountController.ClientRequest clientRequest){

        return new SendMoneyRequest(clientRequest.getSource(), clientRequest.getDestination(), clientRequest.getMoney());
    }

    public AccountController.ClientResponse mapToClientResponse(AccountState accountState){
        return new AccountController.ClientResponse(accountState.getName(), accountState.getMoney(),true);
    }


}
