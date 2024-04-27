package buckpal.hexagonal.member.adapter.in.web;


import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCrudController {

    private final MemberCrudUseCase memberCrudUseCase;

    @PostMapping("/member")
    public String createMember(@RequestBody MemberCreateRequest memberCreateRequest){



        return null;
    }




}
