package buckpal.hexagonal.member.application.service;

import buckpal.hexagonal.member.application.port.in.MemberLoginUseCase;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.application.service.dto.MemberLoginRequest;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
class MemberLoginService implements MemberLoginUseCase {

    private MemberCrudPort memberCrudPort;

    @Override
    public Member login(MemberLoginRequest memberLoginRequest) {
        return memberCrudPort.findByLoginId(memberLoginRequest.getLoginId())
                .filter(member -> member.getPassword().equals(memberLoginRequest.getPassword()))
                .orElseThrow(()-> new NoSuchElementException("login failed"));
    }




}
