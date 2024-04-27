package buckpal.hexagonal.member.application.port.out;

import buckpal.hexagonal.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberCrudPort {


    Optional<Member> findByLoginId(String LoginId);
}
