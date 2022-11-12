package site.metacoding.miniproject.dto.response.person;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PersonRecommendListRespDto {

	private Integer personId;
	private Integer userId;
	private Integer recommendCount;
	private Integer subjectId;
	private String personName;
	private Integer career;
	private String degree;
	private String address;
	private List<String> skill;
	private boolean mark;
}
