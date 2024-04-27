package buckpal.hexagonal.member.application.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest { //create Member 스펙에 의존

    private String name;
    private String loginId;
    private String password;
    private String transferPassword;

}
