package site.metacoding.miniproject.dto.response.company;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CompanyJoinRespDto {
    private String username;
    private String role;
    private Integer userId;
    private String companyName;
    private String companyEmail;
    private String companyPhone;
    private String address;
    private String tech;
    private String ceoName;
}
