package buckpal.hexagonal.member.application.port.in;

import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;

public interface MemberCrudUseCase {


    Member createMember(MemberCreateRequest memberCreateRequest);

    Member findMemberById(Long id);
}
