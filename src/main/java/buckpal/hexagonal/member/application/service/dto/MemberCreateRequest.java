package buckpal.hexagonal.member.application.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    private String name;
    private String loginId;
    private String password;
    private String transferPassword;

}
