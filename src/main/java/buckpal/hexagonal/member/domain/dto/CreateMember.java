package buckpal.hexagonal.member.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMember {

    private String name;
    private String loginId;
    private String password;
    private String transferPassword;

}
