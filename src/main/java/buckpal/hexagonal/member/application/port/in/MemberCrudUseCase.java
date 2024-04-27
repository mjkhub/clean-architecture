package buckpal.hexagonal.member.application.port.in;

import buckpal.hexagonal.member.application.service.dto.MemberCreateRequest;
import buckpal.hexagonal.member.domain.Member;

import java.util.Optional;

public interface MemberCrudUseCase {


    Member createMember(MemberCreateRequest memberCreateRequest);

    Member findMemberById(Long id);
}
