package site.metacoding.miniproject.domain.person_skill;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PersonSkill {
	private Integer personSkillId;
	private Integer personId;
	private String skill;
	private Timestamp createdAt;
	
	@Builder
	public PersonSkill(Integer personSkillId, Integer personId, String skill, Timestamp createdAt) {
		super();
		this.personSkillId = personSkillId;
		this.personId = personId;
		this.skill = skill;
		this.createdAt = createdAt;
	}
}
