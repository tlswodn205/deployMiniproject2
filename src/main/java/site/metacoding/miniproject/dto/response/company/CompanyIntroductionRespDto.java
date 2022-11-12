package site.metacoding.miniproject.dto.response.company;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.miniproject.domain.company.Company;

@Setter
@Getter
public class CompanyIntroductionRespDto {
	private Integer companyId;
	private String companyName;
	private String photo;
	private String introduction;
	private String history;
	private String companyGoal;
	private Integer userId;

	public CompanyIntroductionRespDto(Company company) {
		this.companyId = company.getCompanyId();
		this.companyName = company.getCompanyName();
		this.photo = company.getPhoto();
		this.introduction = company.getIntroduction();
		this.history = company.getHistory();
		this.companyGoal = company.getCompanyGoal();
		this.userId = company.getUserId();
	}
}
