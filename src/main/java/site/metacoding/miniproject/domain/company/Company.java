package site.metacoding.miniproject.domain.company;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Company {
	private Integer companyId;
	private Integer userId;
	private String companyName;
	private String companyEmail;
	private String companyPhone;
	private String tech;
	private String address;
	private String history;
	private String introduction;
	private String photo;
	private String companyGoal;
	private String ceoName;
	private Timestamp createdAt;
	
	public Company(Integer userId, String companyName, String companyEmail, String companyPhone, 
			String address, String tech, String ceoName) {
		super();
		this.userId = userId;
		this.companyName = companyName;
		this.companyEmail = companyEmail;
		this.companyPhone = companyPhone;
		this.tech = tech;
		this.address = address;
		this.ceoName = ceoName;
	}

	@Builder
	public Company(Integer companyId, Integer userId, String companyName, String companyEmail, String companyPhone,
			String tech, String address, String history, String introduction, String photo, String companyGoal,
			String ceoName, Timestamp createdAt) {
		super();
		this.companyId = companyId;
		this.userId = userId;
		this.companyName = companyName;
		this.companyEmail = companyEmail;
		this.companyPhone = companyPhone;
		this.tech = tech;
		this.address = address;
		this.history = history;
		this.introduction = introduction;
		this.photo = photo;
		this.companyGoal = companyGoal;
		this.ceoName = ceoName;
		this.createdAt = createdAt;
	}
}
