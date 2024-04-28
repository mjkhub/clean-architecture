package buckpal.hexagonal.member.application.port.in;

import buckpal.hexagonal.member.application.service.dto.PasswordUpdateRequest;
import buckpal.hexagonal.member.application.service.dto.TransferPasswordUpdateRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;

public interface MemberCrudUseCase {


    Member createMember(MemberCreateRequest memberCreateRequest);

    Member findMemberById(Long id);

    Member updatePassword(Long id, PasswordUpdateRequest passwordUpdateRequest);

    Member updateTransferPassword(Long id, TransferPasswordUpdateRequest transferPasswordUpdateRequest);

}
