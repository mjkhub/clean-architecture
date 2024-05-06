package buckpal.hexagonal.lottery.adapter.in.web;


import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.lottery.application.port.in.LotteryCrudUseCase;
import buckpal.hexagonal.lottery.application.service.dto.CreateLotteryRequest;
import buckpal.hexagonal.lottery.domain.Lottery;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class LotteryCrudController {

    private final LotteryCrudUseCase lotteryCrudUseCase;

    private final SessionManager sessionManager;

    public String createLottery(@RequestBody String depositAccountNumber, HttpServletRequest servletRequest){

        Long memberIdFromSession = sessionManager.getMemberIdFromSession(servletRequest);
        Lottery lottery = lotteryCrudUseCase.createLottery(new CreateLotteryRequest(memberIdFromSession, depositAccountNumber));

        return null;
    }


}
