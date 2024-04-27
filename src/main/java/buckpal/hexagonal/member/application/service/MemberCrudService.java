package buckpal.hexagonal.member.application.service;

import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
class MemberCrudService implements MemberCrudUseCase {

    @Override
    public Member createMember(MemberCreateRequest memberCreateRequest) {
        Member member = Member.createMember(memberCreateRequest);
        System.out.println("member = " + member);
        //persistence 처리만 해주면 된다.
        return member;
    }

}
