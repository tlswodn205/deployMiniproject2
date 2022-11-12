package site.metacoding.miniproject.dto.request.person;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.miniproject.domain.person.Person;
import site.metacoding.miniproject.domain.user.User;

@NoArgsConstructor
@Setter
@Getter
public class PersonJoinReqDto {
	private String username;
	private String password;
	private String role;
	private String personName;
	private String personPhone;
	private String personEmail;
	private boolean isGender;
	private String address;
	private String degree;
	private Integer career;
	private List<String> personSkillList;

	public User toUser() {
		return User.builder().username(this.username).password(this.password).role("person").build();
	}

	public Person toPerson(Integer userId) {
		return Person.builder().userId(userId).personName(this.personName).personPhone(this.personPhone)
				.personEmail(this.personEmail).isGender(this.isGender).address(this.address).degree(this.degree)
				.career(this.career).build();
	}

}
