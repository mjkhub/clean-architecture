package buckpal.hexagonal.member.adapter.in.web.login;


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
    public MemberLoginResponse loginMember(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletRequest servletRequest){

//        Member member = memberLoginUseCase.login(memberLoginRequest);
        HttpSession session = servletRequest.getSession(); //세션 생성
        session.setAttribute("memberId", 1); // 로그인 한 member 의 pk를 저장
                                                                // 로그 아웃 에서 사용 되기 보다는 다른 곳에서 많이 사용됨

        return new MemberLoginResponse(); // Account 객체를 또 dto 로 변환 해야 함
    }

    @PostMapping("/logout")
    public String logoutMember(HttpServletRequest servletRequest){
        //세션처리
        HttpSession session = servletRequest.getSession(false);
        session.invalidate(); // could occur null pointer exception

        return "ok";
    }


    //에러처리 고려




}
