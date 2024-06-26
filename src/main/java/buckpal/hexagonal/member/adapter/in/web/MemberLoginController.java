package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.account.adapter.in.web.AccountCrudControllerAdvice;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginUseCase memberLoginUseCase;

    private final SessionManager sessionManager;

    @PostMapping("/login")
    public RedirectView loginMember(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletRequest servletRequest){

        Member member = memberLoginUseCase.login(memberLoginRequest);
        sessionManager.createSessionAndRestore(servletRequest, member.getId()); // 로그인 한 member 의 pk를 저장
                                                                // 로그 아웃 에서 사용 되기 보다는 다른 곳에서 많이 사용됨
        return new RedirectView("/member/accounts");
    }

    @PostMapping("/logout")
    public String logoutMember(HttpServletRequest servletRequest){
        //세션처리
        sessionManager.invalidateSession(servletRequest);

        return "logout is done";
    }


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult illegalExHandle(NoSuchElementException e) {
        log.error("Login failed", e);
        return new ErrorResult("type", "wrong loginId or password", 401, e.getMessage(), "instance");
    }

    @AllArgsConstructor
    @Getter
    static class ErrorResult {
        private String type;
        private String title;
        private int status;
        private String detail;
        private String instance;
    }


}
