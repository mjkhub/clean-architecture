package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.SessionManager;
import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.service.dto.PasswordUpdateRequest;
import buckpal.hexagonal.member.application.service.dto.TransferPasswordUpdateRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class MemberCrudController {

    private final MemberCrudUseCase memberCrudUseCase;
    private final SessionManager sessionManager;

    @PostMapping("/member")
    public MemberCreateResponse createMember(@RequestBody MemberCreateRequest memberCreateRequest){
        Member member = memberCrudUseCase.createMember(memberCreateRequest); // API 로 반환 해서 멤버를 반환
        return new MemberCreateResponse(member.getName(), member.getSignUpDate());
    }


    @GetMapping("/member")
    public MemberGetResponse getMember(HttpServletRequest httpServletRequest){

        Long memberId = sessionManager.getMemberIdFromSession(httpServletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);

        return new MemberGetResponse(member.getName(), member.getLoginId(), member.getSignUpDate());
    }

    @PatchMapping("/member/password")
    public MemberUpdateResponse updateMemberPassword(HttpServletRequest httpServletRequest, @RequestBody PasswordUpdateRequest passwordUpdateRequest){

        Long memberId = sessionManager.getMemberIdFromSession(httpServletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);

        Member updatedMember = memberCrudUseCase.updatePassword(memberId, passwordUpdateRequest);
        sessionManager.invalidateSession(httpServletRequest); //세션을 끊자.

        return new MemberUpdateResponse(true); // API 스펙은 나중에 구체적으로
    }

    @PatchMapping("/member/t-password")
    public MemberUpdateResponse updateMemberTransferPassword(HttpServletRequest httpServletRequest, @RequestBody TransferPasswordUpdateRequest transferPasswordUpdateRequest){

        Long memberId = sessionManager.getMemberIdFromSession(httpServletRequest);
        Member member = memberCrudUseCase.findMemberById(memberId);
        // API 로 반환 해서 멤버를 반환

        Member updatedMember = memberCrudUseCase.updateTransferPassword(memberId, transferPasswordUpdateRequest);

        return new MemberUpdateResponse(true);// API 스펙은 나중에 구체적으로
    }


    // 삭제하고 다시 가입하면 기존에 등록했던 계좌를 동기화해주는 기능도 생각해 볼 수 있다.

    @AllArgsConstructor
    @Getter
    static class MemberCreateResponse {

        private String name;
        private LocalDate signUpDate;
    }

    @AllArgsConstructor
    @Getter
    static class MemberGetResponse {

        private String name;
        private String loginId;
        private LocalDate signUpDate;
        //  password, 송금 비번을 전달하지는 않지만
        // 이 화면에서 수정이 가능하다
    }

    @AllArgsConstructor
    @Getter
    static class MemberUpdateResponse {

        private boolean status;

    }





}


