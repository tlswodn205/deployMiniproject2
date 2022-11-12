package site.metacoding.miniproject.dto.response.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyMyPageUpdateRespDto {
    private Integer userId;
    private String ceoName;
    private String address;
    private String companyPhone;
    private String companyEmail;
    private String tech;
}
