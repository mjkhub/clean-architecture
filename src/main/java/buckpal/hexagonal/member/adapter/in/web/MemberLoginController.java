package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.member.application.port.in.MemberLoginUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberLoginRequest;
import buckpal.hexagonal.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginUseCase memberLoginUseCase;

    private final SessionManager sessionManager;

    @PostMapping("/login")
    public MemberLoginResponse loginMember(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletRequest servletRequest){

        Member member = memberLoginUseCase.login(memberLoginRequest);
        sessionManager.createSessionAndRestore(servletRequest, member.getId()); // 로그인 한 member 의 pk를 저장
                                                                // 로그 아웃 에서 사용 되기 보다는 다른 곳에서 많이 사용됨

        return new MemberLoginResponse(member.getName(), member.getTotalMoney().get(), member.getAccounts()); // Account 객체를 또 dto 로 변환 해야 함
    }

    @PostMapping("/logout")
    public String logoutMember(HttpServletRequest servletRequest){
        //세션처리
        HttpSession session = servletRequest.getSession(false);
        session.invalidate(); // could occur null pointer exception

        return "ok";
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class MemberLoginResponse {

        private String name;
        private int totalMoney;
        private List<Account> accounts;

    }



}
