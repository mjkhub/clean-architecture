package buckpal.hexagonal.member.application.service;

import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.CreateMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class MemberCrudService implements MemberCrudUseCase {

    private final MemberCrudPort memberCrudPort;

    @Transactional
    @Override
    public Member createMember(MemberCreateRequest request) {
        CreateMember createMember = mapToDomainCreateRequest(request);
        Member member = Member.createMember(createMember);
        log.info("member = {}", member);
        return memberCrudPort.save(member);
    }

    @Override
    public Member findMemberById(Long id) {

        return memberCrudPort.findById(id).orElseThrow(); //없으면 예외를 터트려
    }



    private CreateMember mapToDomainCreateRequest(MemberCreateRequest request) {

        return new CreateMember(request.getName(), request.getLoginId(), request.getPassword(), request.getTransferPassword());
    }


}
