package buckpal.hexagonal.member.adapter.in.web.crud;


import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCrudController {

    private final MemberCrudUseCase memberCrudUseCase;

    @PostMapping("/member")
    public MemberCreateResponse createMember(@RequestBody MemberCreateRequest memberCreateRequest){
        Member member = memberCrudUseCase.createMember(memberCreateRequest);
        // API 로 반환 해서 멤버를 반환
        return new MemberCreateResponse(member.getName());
    }






}
