package site.metacoding.miniproject.dto.response.person;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonJoinRespDto {
    private String username;
    private String role;
    private String personName;
    private String personPhone;
    private String personEmail;
    private boolean isGender;
    private String address;
    private String degree;
    private Integer career;
    private List<String> personSkillList;
}
