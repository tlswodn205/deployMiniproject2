package site.metacoding.miniproject.dto.response.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InterestPersonRespDto {
	private Integer personId;
	private boolean Mark;
	private Integer recommendCount;
	private String personName;
	private Integer career;
	private String degree;
	private String address;
	private List<String> personSkillList;
}
