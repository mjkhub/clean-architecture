package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.port.in.MemberLoginUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.application.service.dto.MemberLoginRequest;
import buckpal.hexagonal.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginUseCase memberLoginUseCase;

    @PostMapping("/login")
    public String loginMember(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletRequest servletRequest){

        //세션처리

        Member member = memberLoginUseCase.login(memberLoginRequest);
        HttpSession session = servletRequest.getSession();
        session.setAttribute("memberId", member.getId());

        return id;
    }

    @PostMapping("/logout")
    public String logoutMember(HttpServletRequest servletRequest){
        //세션처리
        HttpSession session = servletRequest.getSession(false);
        Object memberId = session.getAttribute("memberId");
        System.out.print("session.getId() = " + session.getId());
        System.out.println("memberId = " + memberId);
        session.invalidate();
        return "ok";
    }



//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandle(IllegalArgumentException e) {
//        log.error("[exceptionHandle] ex", e);
//        return new ErrorResult("BAD", e.getMessage());
//    }
//







}
