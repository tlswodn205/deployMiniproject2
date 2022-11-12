package site.metacoding.miniproject.dto.request.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class CompanyMyPageUpdateReqDto {
	private Integer userId;
	private String password;
	private String ceoName;
	private String address;
	private String companyPhone;
	private String companyEmail;
	private String tech;
}
