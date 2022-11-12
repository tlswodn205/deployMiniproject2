package site.metacoding.miniproject.dto.response.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class CompanyMyPageRespDto {
	private Integer userId;
	private Integer companyId;
	private String username;
	private String password;
	private String companyName;
	private String ceoName;
	private String address;
	private String companyPhone;
	private String companyEmail;
	private String tech;
}
