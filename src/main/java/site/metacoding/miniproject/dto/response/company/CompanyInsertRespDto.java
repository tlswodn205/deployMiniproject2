package site.metacoding.miniproject.dto.response.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompanyInsertRespDto {
	private Integer companyId;
	private String photo;
	private String introduction;
	private String history;
	private String companyGoal;
}
