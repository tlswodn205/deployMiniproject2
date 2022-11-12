package site.metacoding.miniproject.domain.need_skill;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NeedSkill {
	private Integer needSkillId;
	private Integer noticeId;
	private String skill;
	private Timestamp createdAt;
	
	@Builder
	public NeedSkill(Integer needSkillId, Integer noticeId, String skill, Timestamp createdAt) {
		super();
		this.needSkillId = needSkillId;
		this.noticeId = noticeId;
		this.skill = skill;
		this.createdAt = createdAt;
	}
	
}
