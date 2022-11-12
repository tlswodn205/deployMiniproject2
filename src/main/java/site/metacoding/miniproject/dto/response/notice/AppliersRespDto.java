package site.metacoding.miniproject.dto.response.notice;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppliersRespDto {
	private Integer resumeId;
	private Integer personId;
	private String personName;
	private Integer career;
	private List<String> personSkillList;
	private Timestamp createdAt;
}
