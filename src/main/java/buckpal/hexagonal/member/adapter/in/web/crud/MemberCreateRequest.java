package buckpal.hexagonal.member.adapter.in.web;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
class MemberCreateRequest {

    private String name;
    private String loginId;
    private String password;
    private String transferPassword;
}
