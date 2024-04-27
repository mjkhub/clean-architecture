package buckpal.hexagonal.member.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {

    private String loginId;
    private String password;
}
