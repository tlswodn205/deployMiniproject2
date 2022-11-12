package site.metacoding.miniproject.dto.request.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.company.Company;
import site.metacoding.miniproject.domain.user.User;

@NoArgsConstructor
@Setter
@Getter
public class CompanyJoinReqDto {
	private String username;
	private String password;
	private String role;
	private Integer userId;
	private String companyName;
	private String companyEmail;
	private String companyPhone;
	private String address;
	private String tech;
	private String ceoName;

	public User toUser() {
		return User.builder().username(this.username).password(this.password).role("company").build();
	}

	public Company toCompany(Integer userId) {
		return new Company(userId, this.companyName, this.companyEmail, this.companyPhone,
				this.address, this.tech, this.ceoName);
	}
}