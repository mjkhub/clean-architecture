package buckpal.hexagonal.member.adapter.out.persistence;

import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.application.service.dto.PasswordUpdateRequest;
import buckpal.hexagonal.member.application.service.dto.TransferPasswordUpdateRequest;
import buckpal.hexagonal.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class MemberCrudAdapter implements MemberCrudPort{

    private final MemberJpaRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    @Override
    public Member updatePassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updatePassword(passwordUpdateRequest.getPassword());
        return member;
    }

    @Override
    public Member updateTransferPassword(Long id, TransferPasswordUpdateRequest transferPasswordUpdateRequest) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateTransferPassword(transferPasswordUpdateRequest.getTransferPassword());



        return member;
    }
}
