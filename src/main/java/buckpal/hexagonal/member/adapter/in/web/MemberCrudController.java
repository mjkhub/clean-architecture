package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class MemberCrudController {

    private final MemberCrudUseCase memberCrudUseCase;

    @PostMapping("/member")
    public MemberCreateResponse createMember(@RequestBody MemberCreateRequest memberCreateRequest){
        Member member = memberCrudUseCase.createMember(memberCreateRequest);
        // API 로 반환 해서 멤버를 반환
        return new MemberCreateResponse(member.getName(), member.getSignUpDate());
    }

    //Member 를 가져오라는 api

    // Member 를 수정해오라는 api

    // member 를 삭제 해오라는 api

    // 삭제하고 다시 가입하면 기존에 등록했던.. 걸 다시? 아 근데 이건쫌 ㅋㅋ

    @AllArgsConstructor
    @Getter
    static class MemberCreateResponse {

        private String name;
        private LocalDate signUpDate;
    }
}


