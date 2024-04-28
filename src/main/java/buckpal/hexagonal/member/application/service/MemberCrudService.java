package buckpal.hexagonal.member.application.service;

import buckpal.hexagonal.member.application.port.in.MemberCrudUseCase;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.application.service.dto.PasswordUpdateRequest;
import buckpal.hexagonal.member.application.service.dto.TransferPasswordUpdateRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;
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
    public Member createMember(MemberCreateRequest memberCreateRequest) {

        Member member = Member.createMember(memberCreateRequest);
        log.info("member = {}", member);
        return memberCrudPort.save(member);
    }

    @Override
    public Member findMemberById(Long id) {

        return memberCrudPort.findById(id).orElseThrow(); //없으면 예외를 터트려
    }

    @Transactional
    @Override
    public Member updatePassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {

        return memberCrudPort.updatePassword(id, passwordUpdateRequest);
    }

    @Transactional
    @Override
    public Member updateTransferPassword(Long id, TransferPasswordUpdateRequest transferPasswordUpdateRequest) {
        return memberCrudPort.updateTransferPassword(id, transferPasswordUpdateRequest);
    }
}
