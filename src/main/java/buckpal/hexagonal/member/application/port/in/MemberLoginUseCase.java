package buckpal.hexagonal.member.application.port.in;

import buckpal.hexagonal.member.application.service.dto.MemberLoginRequest;
import buckpal.hexagonal.member.domain.Member;

public interface MemberLoginUseCase {

    Member login(MemberLoginRequest memberLoginRequest);

}
