package buckpal.hexagonal.member.application.port.out;

import buckpal.hexagonal.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberCrudPort {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByLoginId(String loginId);

}
