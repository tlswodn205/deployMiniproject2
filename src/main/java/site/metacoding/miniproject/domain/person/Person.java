package site.metacoding.miniproject.domain.person;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Person {
	private Integer personId;
	private Integer userId;
	private String personName;
	private String personEmail;
	private String personPhone;
	private boolean isGender;
	private String address;
	private String degree;
	private Integer career;
	private Timestamp createdAt;
	
	@Builder
	public Person(Integer personId, Integer userId, String personName, String personEmail, String personPhone,
			boolean isGender, String address, String degree, Integer career, Timestamp createdAt) {
		super();
		this.personId = personId;
		this.userId = userId;
		this.personName = personName;
		this.personEmail = personEmail;
		this.personPhone = personPhone;
		this.isGender = isGender;
		this.address = address;
		this.degree = degree;
		this.career = career;
		this.createdAt = createdAt;
	}

}
